<template>
  <div class="ac">
  <el-button @click="add()" type="info" style="margin-top: 5%">添加新用户</el-button>
  <el-table :data="users"
            highlight-current-row="true"
            height="400"
            style="width: 100%;margin-top: 3%"
            :row-class-name="rn"
  >
    <!--    <el-table-column fixed prop="bookPicture" label="封面" width="200px">-->
    <!--      <template v-slot="scope">-->
    <!--        <el-image :src="require('@/assets/static/'+scope.row.bookPicture+'.jpg')"></el-image>-->
    <!--      </template>-->
    <!--    </el-table-column>-->
    <el-table-column prop="userId" label="编号" width="100px"></el-table-column>
    <el-table-column prop="userName" class-name="userName" label="用户名" width="200px"></el-table-column>
    <el-table-column prop="userPetName" label="昵称" width="200px"></el-table-column>
    <el-table-column prop="userPassword" label="密码" width="100px"></el-table-column>
    <el-table-column prop="userTel" label="电话" width="200px"></el-table-column>
    <el-table-column prop="userAddress" label="地址" width="200px"></el-table-column>
    <el-table-column prop="userId" label="操作" width="150px">
      <template v-slot="scope">
        <el-button color="#626aef" round class="el-button" @click="alter(scope.row.userId)">修改</el-button>
        <el-button type="danger" round class="el-button" @click="del(scope.row.userId)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  </div>
</template>
<script setup>
import {ref} from "vue";
import {useRouter} from "vue-router";
import axios from 'axios'
let users = ref([])
let router = useRouter()

axios.get('api/users').then(Response => {
  console.log("已经在获取users")
  users.value = Response.data
})


//注意这是解构
const rn=({row,rowIndex})=>{
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
  console.log(id.value+"给用户修改修改传递参数")
  router.push({
    name: 'alterUser',
    query: {
      userId: id.value
    }
  })
})

let del = ((v) => {
  // axios.post(`api/user`,
  //     {_method:'delete',userId:`${v}`}
  console.log(v + "这里是用户删除")
  if (confirm("确认删除?")) {
    axios.delete(`api/user?userId=${v}`,
    ).then(Response => {
      let message = Response.data
      alert(message)
      axios.get('api/users').then(Response => {
        users.value = Response.data
      })
    }).catch(Error=>{
      alert(Error.message+"请稍后重试!")
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
.ac{
  margin: 3% 5%;
}
.el-button{
  margin-top: 2%;
}
</style>
