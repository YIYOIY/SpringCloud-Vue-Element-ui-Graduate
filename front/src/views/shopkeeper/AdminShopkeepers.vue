<template>
  <div class="background">
    <el-button @click="add()" size="large" plain round type="success" style="top: 15.5%;left:40%;position: fixed">
      添加新企业
    </el-button>
    <el-button @click="search(searchName)" style="top: 16%;left: 25%;position: absolute" plain round type="info">
      查找企业
    </el-button>
    <el-button @click="search('all')" style="top: 16%;left: 32%;position: absolute" plain round type="primary">
      全部企业
    </el-button>
    <el-form>
      <el-form-item label="企业名称" prop="shopkeeperName" style="top: 16%;left: 5%;position: absolute">
        <el-input type="text" v-model="searchName" autofocus maxlength="20" clearable
                  style="max-width: 200px" placeholder="输入企业名"></el-input>
      </el-form-item>
    </el-form>


    <el-table :data="shopkeepers" :highlight-current-row="true" height="400" width="1000" style="margin-top: 13%" tooltip-effect="light">
      <el-table-column fixed prop="id" label="企业ID" :show-overflow-tooltip="true" sortable width="150px"></el-table-column>

      <el-table-column fixed prop="image.picture" label="头像" width="150px">
        <template v-slot="scope">
          <el-image style="width: 100px; height: 100px;padding: 0 0 0 15%" :src="scope.row.image==null?'img/未设置图片时的404.jpg':scope.row.image.picture" fit="cover"/>
        </template>
      </el-table-column>

      <el-table-column fixed prop="shopkeeperName" class-name="shopkeeperName" label="企业名" :show-overflow-tooltip="true" width="150px"/>
      <el-table-column fixed prop="shopkeeperQuality" label="企业性质" width="120px"/>
      <el-table-column fixed prop="shopkeeperMoney" label="账户余额" sortable width="150px">
        <template v-slot="scope">
          RMB  <el-tag effect="plain" type="warning" size="large">
          {{scope.row.shopkeeperMoney>0?scope.row.shopkeeperMoney:0}}</el-tag>  元
        </template>
      </el-table-column>
      <el-table-column fixed prop="shopkeeperPhone" label="联系方式" width="150px"/>
      <el-table-column fixed prop="shopkeeperSignTime" label="平台注册日期"  :show-overflow-tooltip="true" width="200px">
        <template v-slot="scope">
          {{ scope.row.shopkeeperSignTime.substr(0,10)+"  "+scope.row.shopkeeperSignTime.substr(11,13) }}
        </template>
      </el-table-column>
      <el-table-column fixed prop="shopkeeperBirth" label="企业注册日期" sortable width="200"/>
      <el-table-column fixed prop="shopkeeperAddress" label="公司地址" :show-overflow-tooltip="true" width="150"/>
      <el-table-column fixed prop="id" label="操作" width="220px">
        <template v-slot="scope">
          <el-button link round plain type="primary"  @click="alter(scope.row.id)">详细信息</el-button>
          <el-button link round plain type="danger"   @click="del(scope.row.id)">删除信息</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script setup>
import {onMounted, reactive, ref} from "vue";
import {deleteShopkeeper, adminGetShopkeepers} from "@/api/ShopkeeperApi";
import {useStore} from "vuex";
import {useRouter} from "vue-router";
import {ElMessage, ElNotification} from "element-plus";

let router=useRouter()
const store = useStore()

let shopkeepers = ref([])

let pageNo = ref(1)
let pageSize = ref(5)
let searchName = ref('')

let search = ((v) => {
  if (v==='') v = null
  adminGetShopkeepers(store.state.adminId, pageNo.value, pageSize.value, v).then(Response => {
    shopkeepers.value = Response.data.data
  }).catch((error)=>{
    if (error.data.code === 404) {
      ElNotification({
        title: '未注册',
        message: error.data.message,
        type: "error",
        position: "top-left",
        offset: 100,
      })
      shopkeepers.value = null
    }
  })
})

onMounted(()=>{
  adminGetShopkeepers(store.state.adminId, pageNo.value, pageSize.value, "all").then(Response => {
    // 因为又在统一返回上封装了分页，所以多了一层data
    shopkeepers.value = Response.data.data
    console.log(shopkeepers.value)
  })
})


let alter = ((v) => {
  let id = ref(v)
  router.push({
    name: 'alterShopkeeper',
    query: {
      shopkeeperId: id.value
    }
  })
})

const deleteShop = reactive({
  shopkeeper: {
    id: ''
  }
})

// 转换成json对象，后端才会解析成shopkeeper对象
let del = ((v) => {
  deleteShop.shopkeeper.id = v
  if (confirm("确认删除?")) {
    deleteShopkeeper(JSON.stringify(deleteShop.shopkeeper)).then(Response => {
      ElMessage.success(Response.message)
      adminGetShopkeepers(store.state.adminId, pageNo.value, pageSize.value, "all").then(Response => {
        shopkeepers.value = Response.data.data
      })
    }).catch(Error => {
      ElMessage.error(Error.data.message + "请稍后重试!")
    })
  }
})

let add = (() => {
  router.push({
    name: 'addShopkeeper'
  })
})

</script>
<style scoped>
/deep/ .el-table__cell {
  font-size: 18px;
}

.background {
  margin: 0 3.5%;
}
</style>
