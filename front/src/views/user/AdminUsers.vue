<template>
  <div class="ac">
    <el-button @click="add()"  plain round type="warning" style="margin-top: 5%">添加新用户</el-button>
    <el-table :data="users" :highlight-current-row="true" height="100%" style="width: 100%;margin-top: 3%" label-width="30%" >
      <el-table-column prop="userId" label="编号" sortable></el-table-column>
      <el-table-column prop="userName" class-name="userName" label="用户名"></el-table-column>
      <el-table-column prop="userSex" label="性别"></el-table-column>
      <el-table-column prop="userPassword" label="密码"></el-table-column>
      <el-table-column prop="userPhone" label="电话"></el-table-column>
      <el-table-column prop="userAddress" label="地址"></el-table-column>
      <el-table-column prop="userBirth" label="生日" sortable></el-table-column>
      <el-table-column prop="userId" label="操作">
        <template v-slot="scope">
          <el-button type="success" round plain class="el-button" @click="alter(scope.row.userId)">修改</el-button>
          <el-button type="primary" round plain class="el-button" @click="del(scope.row.userId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script setup>
import {ref} from "vue";
import {useRouter} from "vue-router";
import {deleteUser, getUsers} from "@/api/UserApi";
import {useStore} from "vuex";
import {ElMessage, ElNotification} from "element-plus";

const store = useStore()
let router = useRouter()
let users = ref([])

getUsers().then(Response => {
  users.value = Response.data
})


let alter = ((v) => {
  let id = ref(v)
  router.push({
    name: 'alterUser',
    query: {
      userId: id.value
    }
  })
})


let del = ((v) => {
  if (confirm("确认删除?")) {
    deleteUser(v).then(Response => {
      if (store.state.userId === v) {
        router.push("/login")
      }
      ElNotification({
            message: Response.message,
            title: '删除成功！',
            type: 'success',
            position: "top-right"
          }
      )
      getUsers().then(Response => {
        users.value = Response.data
      })
    }).catch(Error => {
      ElMessage.error(Error.data.message + "请稍后重试!")
    })
  }
})

let add = (() => {
  router.push({
    name: 'addUser'
  })
})

</script>
<style scoped>
/deep/ .el-table__cell{
  font-size: 20px;
  padding-left: 1%
}

.el-table .el-button {
  margin-left: 10%;
  width: 40%;
}
.ac {
  margin: 3% 5%;
}
</style>
