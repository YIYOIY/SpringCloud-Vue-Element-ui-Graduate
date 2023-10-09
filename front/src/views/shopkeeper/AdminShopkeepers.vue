<template>
  <div class="background">
    <el-button @click="add()" size="large" plain round type="success" style="top: 15.5%;left:40%;position: fixed">
      添加新企业
    </el-button>
    <el-button @click="search(searchName)" style="top: 16%;left: 25%;position: absolute" plain round type="info">
      查找企业
    </el-button>
    <el-button @click="search('')" style="top: 16%;left: 32%;position: absolute" plain round type="primary">
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
          <el-image style="width: 100px; height: 100px;padding: 0 0 0 15%" :src="scope.row.image?scope.row.image.picture:'img/未设置图片时的404.jpg'" fit="cover"/>
        </template>
      </el-table-column>

      <el-table-column fixed prop="shopkeeperName" class-name="shopkeeperName" label="企业名" :show-overflow-tooltip="true" width="150px"/>
      <el-table-column fixed prop="shopkeeperQuality" label="企业性质" width="120px"/>
      <el-table-column fixed prop="shopkeeperMoney" label="账户余额" sortable width="200px">
        <template v-slot="scope">
         <el-tag effect="plain" type="warning" size="large">
          {{scope.row.shopkeeperMoney>0?scope.row.shopkeeperMoney:0}} 元</el-tag>
        </template>
      </el-table-column>
      <el-table-column fixed prop="shopkeeperPhone" label="联系方式" width="150px"/>
      <el-table-column fixed prop="shopkeeperSignTime" label="平台注册日期"  :show-overflow-tooltip="true" width="350px">
        <template v-slot="scope">
          <el-tooltip placement="bottom" effect="light">
            <template #content>
              <el-date-picker style="width: 150%;" v-model="scope.row.shopkeeperSignTime" type="datetime" format="YYYY 年 MM 月 DD 日 HH 时 mm 分 ss 秒"/>
            </template>
            <el-button plain round type="info" >
              {{  scope.row.shopkeeperSignTime}}
            </el-button>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column fixed prop="shopkeeperBirth" label="企业注册日期" sortable width="220"/>
      <el-table-column fixed prop="shopkeeperAddress" label="公司地址" :show-overflow-tooltip="true" width="560"/>
      <el-table-column fixed prop="id" label="操作" width="190px">
        <template v-slot="scope">
          <el-button link round plain type="primary"  @click="alter(scope.row.id)">详细信息</el-button>
          <el-button link round plain type="danger"   @click="del(scope.row.id)">删除信息</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        v-model:current-page="pageNo"
        v-model:page-size="pageSize"
        :page-sizes="[1,5, 10, 15,20,30,50,100,200,400,1000]"
        background
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />
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
let total = ref(1)
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
  adminGetShopkeepers(store.state.adminId, pageNo.value, pageSize.value, null).then(Response => {
    // 因为又在统一返回上封装了分页，所以多了一层data
    shopkeepers.value = Response.data.data
    pageSize.value = Response.data.pageSize
    total.value = parseInt(Response.data.total)
    pageNo.value = Response.data.current
    console.log(shopkeepers.value)
  })
})

let handleSizeChange = ((val) => {
  adminGetShopkeepers(store.state.adminId, pageNo.value, val, null).then(Response => {
    shopkeepers.value = Response.data.data
    pageSize.value = Response.data.pageSize
    total.value = parseInt(Response.data.total)
    pageNo.value = Response.data.current
  })
})

let handleCurrentChange = ((val) => {
  adminGetShopkeepers(store.state.adminId, val, pageSize.value, null).then(Response => {
    shopkeepers.value = Response.data.data
    pageSize.value = Response.data.pageSize
    total.value = parseInt(Response.data.total)
    pageNo.value = Response.data.current
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
      adminGetShopkeepers(store.state.adminId, pageNo.value, pageSize.value, null).then(Response => {
        shopkeepers.value = Response.data.data
        pageSize.value = Response.data.pageSize
        total.value = parseInt(Response.data.total)
        pageNo.value = Response.data.current
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
  margin: 0 2.5%;
}
</style>
