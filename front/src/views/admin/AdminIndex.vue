<template>
  <div class="ac">
    <el-button @click="add()" plain round type="warning"  style="margin-top: 5%">添加新管理员</el-button>
    <el-table :data="admins" :highlight-current-row="true" height="100%" style="width: 100%;margin-top: 3%" label-width="30%" >
      <el-table-column prop="adminId" label="管理员ID" sortable></el-table-column>
      <el-table-column prop="adminName"  label="管理员名称"></el-table-column>
      <el-table-column prop="adminPassword" label="管理员密码"></el-table-column>
      <el-table-column prop="adminId" label="操作">
        <template v-slot="scope">
          <el-button type="success" plain round class="el-button" @click="alter(scope.row.adminId)">修改</el-button>
          <el-button type="primary" plain round class="el-button" @click="del(scope.row.adminId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

  </div>
</template>
<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import {deleteAdmin,getAdmin} from "@/api/AdminApi";
import {ElMessage} from "element-plus";
import store from "@/store";

let admins = ref([])
let router = useRouter()

getAdmin().then(Response => {
  admins.value = Response.data
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

let del = ((v) => {
  if (confirm("确认删除?")) {
    deleteAdmin(v).then(Response => {
      ElMessage.success(Response.message)
      if (store.state.adminId === v) {
        router.push("/login")
      }else{
        getAdmin().then(Response => {
          admins.value = Response.data
        })
      }
    }).catch(Error => {
      ElMessage.error(Error.data.message + "删除失败,请稍后重试!")
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
/deep/ .el-table__cell{
  font-size: 20px;
  padding-left: 5%
}

.el-table .el-button {
  margin-left: 10%;
  width: 40%;
}

.ac {
  margin: 3% 5%;
}
</style>
