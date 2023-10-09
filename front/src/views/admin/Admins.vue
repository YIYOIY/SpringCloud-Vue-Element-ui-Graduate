<template>
  <div class="background">
    <el-button @click="add()" size="large" plain round type="success" style="top: 15.5%;left:40%;position: fixed">
      添加新管理员
    </el-button>
    <el-button @click="search(searchName)" style="top: 16%;left: 25%;position: absolute" plain round type="info">
      查找管理员
    </el-button>
    <el-button @click="search('')" style="top: 16%;left: 32%;position: absolute" plain round type="primary">
      全部管理员
    </el-button>
    <el-form>
      <el-form-item label="管理员名称" prop="adminName" style="top: 16%;left: 5%;position: absolute">
        <el-input type="text" v-model="searchName" autofocus maxlength="20" clearable
                  style="max-width: 200px" placeholder="输入管理员名"></el-input>
      </el-form-item>
    </el-form>


    <el-table :data="admins" :highlight-current-row="true" height="400"  style="margin-top: 13%"
              tooltip-effect="light">
      <el-table-column fixed prop="id" label="管理员ID" :show-overflow-tooltip="true" sortable
                       width="150px"></el-table-column>

      <el-table-column fixed prop="image.picture" label="头像" width="150px">
        <template v-slot="scope">
          <el-image style="width: 100px; height: 100px;padding: 0 0 0 15%"
                    :src="scope.row.image?scope.row.image.picture:'img/未设置图片时的404.jpg'" fit="cover"/>
        </template>
      </el-table-column>

      <el-table-column fixed prop="adminName" class-name="adminName" label="管理员名" :show-overflow-tooltip="true"
                       width="150px"/>
      <el-table-column fixed prop="adminSex" label="性别" width="80px"/>
      <el-table-column fixed prop="adminMoney" label="账户余额" sortable width="200px">
        <template v-slot="scope">
          <el-tag effect="plain" type="warning" size="large">
            {{ scope.row.adminMoney > 0 ? scope.row.adminMoney : 0 }}元
          </el-tag>

        </template>
      </el-table-column>
      <el-table-column fixed prop="adminPhone" label="联系方式" width="150px"/>
      <el-table-column fixed prop="adminSignTime" label="注册时间" :show-overflow-tooltip="true" width="200px">
        <template v-slot="scope">
          {{ scope.row.adminSignTime.substr(0, 10) + "  " + scope.row.adminSignTime.substr(11, 13) }}
        </template>
      </el-table-column>
      <el-table-column fixed prop="adminBirth" label="出生日期" sortable width="150"/>
      <el-table-column fixed prop="adminAddress" label="收货地址" :show-overflow-tooltip="true" width="150"/>
      <el-table-column fixed prop="id" label="操作" width="220px">
        <template v-slot="scope">
          <el-button link round plain type="primary" @click="alter(scope.row.id)">详细信息</el-button>
          <el-button link round plain type="danger" @click="del(scope.row.id)">删除信息</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        v-model:current-page="pageNo"
        v-model:page-size="pageSize"
        :page-sizes="[5, 10, 15,20,30,50,100,200,400,1000]"
        background
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />
  </div>
</template>
<script setup>
import {reactive, ref} from "vue";
import {useRouter} from "vue-router";
import {deleteAdmin, adminGetAdmins} from "@/api/AdminApi";
import {useStore} from "vuex";
import {ElMessage, ElNotification} from "element-plus";

const store = useStore()
let router = useRouter()
let admins = ref([])

let pageNo = ref(1)
let pageSize = ref(5)
let total = ref(1)
let searchName = ref('')

let search = ((v) => {
  if (v === '') v = null
  adminGetAdmins(store.state.adminId, pageNo.value, pageSize.value, v).then(Response => {
    admins.value = Response.data.data
  }).catch((error)=>{
    if (error.data.code === 404) {
      ElNotification({
        title: '未注册',
        message: error.data.message,
        type: "error",
        position: "top-left",
        offset: 100,
      })
      admins.value = null
    }
  })
})

adminGetAdmins(store.state.adminId, pageNo.value, pageSize.value, null).then(Response => {
  // 因为又在统一返回上封装了分页，所以多了一层data
  admins.value = Response.data.data
  pageSize.value = Response.data.pageSize
  total.value = parseInt(Response.data.total)
  pageNo.value = Response.data.current
  console.log(admins.value)
})
let handleSizeChange = ((val) => {
  adminGetAdmins(store.state.adminId, pageNo.value, val, null).then(Response => {
    admins.value = Response.data.data
    pageSize.value = Response.data.pageSize
    total.value = parseInt(Response.data.total)
    pageNo.value = Response.data.current
  })
})

let handleCurrentChange = ((val) => {
  adminGetAdmins(store.state.adminId, val, pageSize.value, null).then(Response => {
    admins.value = Response.data.data
    pageSize.value = Response.data.pageSize
    total.value = parseInt(Response.data.total)
    pageNo.value = Response.data.current
  })
})

let alter = ((v) => {
  let id = ref(v)
  router.push({
    name: 'alterAdmin',
    query: {
      adminId: id.value
    }
  })
})

const delAdmin = reactive({
  admin: {
    id: ''
  }
})

// 转换成json对象，后端才会解析成admin对象
let del = ((v) => {
  delAdmin.admin.id = v
  if (confirm("确认删除?")) {
    deleteAdmin(JSON.stringify(delAdmin.admin)).then(Response => {
      ElMessage.success(Response.message)
      if (v === store.state.adminId) {
        router.push({
          name: 'login'
        })
      } else {
        adminGetAdmins(store.state.adminId, pageNo.value, pageSize.value, null).then(Response => {
          admins.value = Response.data.data
          pageSize.value = Response.data.pageSize
          total.value = parseInt(Response.data.total)
          pageNo.value = Response.data.current
          console.log(admins.value)
        })
      }
    }).catch(Error => {
      ElMessage.error(Error.data.message + "请稍后重试!")
    })
  }
})

let add = (() => {
  router.push({
    name: 'addAdmin'
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
