<template>
  <div class="ac">
    <el-button @click="add()" type="info" style="margin-top: 5%">添加新用户</el-button>

    <el-table :data="users" highlight-current-row="true" height="100%" style="width: 100%;margin-top: 3%"
      label-width="20%" :row-class-name="rn">
      <el-table-column prop="userId" label="编号"></el-table-column>
      <el-table-column prop="userName" class-name="userName" label="用户名"></el-table-column>
      <el-table-column prop="userSex" label="性别"></el-table-column>
      <el-table-column prop="userPassword" label="密码"></el-table-column>
      <el-table-column prop="userPhone" label="电话"></el-table-column>
      <el-table-column prop="userAddress" label="地址"></el-table-column>
      <el-table-column prop="userBirth" label="生日"></el-table-column>
      <el-table-column prop="userId" label="操作">
        <template v-slot="scope">
          <el-button type="success" round class="el-button" @click="alter(scope.row.userId)">修改</el-button>
          <el-button type="primary" round class="el-button" @click="del(scope.row.userId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import axios from 'axios'
let users = ref([])
let router = useRouter()

axios.get('api/users').then(Response => {
  console.log("已经在获取users")
  users.value = Response.data
})


//注意这是解构
const rn = ({ row, rowIndex }) => {
  // console.log(row)
  // console.log(rowIndex)
  if (rowIndex % 2 !== 0) {
    return 'light-row'
  } else {
    return 'aterrimus-row'
  }
}

let alter = ((v) => {
  console.log(v + "这里是用户修改")
  let id = ref(v)
  console.log(id.value + "给用户修改修改传递参数")
  router.push({
    name: 'alterUser',
    query: {
      userId: id.value
    }
  })
})

import { useStore } from "vuex";
const store = useStore()

let del = ((v) => {
  // axios.post(`api/user`,
  //     {_method:'delete',userId:`${v}`}
  console.log(v + "这里是用户删除")
  if (confirm("确认删除?")) {
    axios.delete(`api/user?userId=${v}`,
    ).then(Response => {
      let message = Response.data
      if (store.state.userId == v) {
        sessionStorage.removeItem('user')
        store.state.userId = '';
        store.state.userName = '';
        store.state.userPassword = '';
        store.state.isUser = false;
      }
      alert(message)

      axios.get('api/users').then(Response => {
        users.value = Response.data
      })
    }).catch(Error => {
      alert(Error.message + "请稍后重试!")
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
/deep/ .userName .cell {
  padding-left: 30px;
}

.el-table .el-button {
  margin-left: 10%;
  width: 80%;
}

/deep/ .light-row {
  background: #d9e6ea;
}

/deep/ .aterrimus-row {
  background: #e7ecef;
}

.ac {
  margin: 3% 5%;
}

.el-button {
  margin-top: 2%;
}
</style>
