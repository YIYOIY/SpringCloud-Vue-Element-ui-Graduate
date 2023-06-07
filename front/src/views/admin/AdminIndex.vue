<template>
  <div class="ac">


  <el-button @click="add()" type="info" style="margin-top: 5%">添加新管理员</el-button>
  <el-table :data="admins"
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
    <el-table-column prop="adminName" class-name="adminName" label="用户名" width="250px"></el-table-column>
    <el-table-column prop="adminPetName" label="昵称" width="250px"></el-table-column>
    <el-table-column prop="adminPassword" label="密码" width="200px"></el-table-column>
    <el-table-column prop="adminId" label="编号" width="200px"></el-table-column>
    <el-table-column prop="adminId" label="操作" width="150px">
      <template v-slot="scope">
        <el-button color="#626aef" round class="el-button" @click="alter(scope.row.adminId)">修改</el-button>
        <el-button type="danger" round  class="el-button" @click="del(scope.row.adminId)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>

  </div>
</template>
<script setup>
import {ref} from "vue";
import {useRouter} from "vue-router";
import axios from 'axios'

let admins = ref([])
let router = useRouter()
axios.get('api/admin').then(Response => {
  admins.value = Response.data
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
  console.log(v + "这里是管理员修改")
  let id = ref(v)
  console.log(id.value+"给修改传递参数")
  router.push({
    name: 'alterAdmin',
    query: {
      adminId: id.value
    }
  })
})

let del = ((v) => {
  console.log(v + "这里是管理员删除")
  if (confirm("确认删除?")) {
    axios.delete(`api/admin?adminId=${v}`).then(Response => {
      let message = Response.data
      alert(message)
      axios.get('api/admin').then(Response => {
        admins.value = Response.data
      })
    }).catch(Error=>{
      alert(Error.message+"删除失败,请稍后重试!")
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
/deep/ .adminName .cell {
  padding-left: 30px;
}
.el-table .el-button {
  margin-left: 10%;
  width: 80%;
}
/deep/ .light-row {
  background: #f1f1f5;
}

/deep/ .aterrimus-row {
  background: #eaecef;
}
.ac{
  margin: 3% 5%;
}
.el-button{
  margin-top: 2%;
}
</style>
