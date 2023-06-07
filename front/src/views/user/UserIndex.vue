<template>
  <div style="margin: 10% 2%;text-align: center;align-content: center">
    <div class="control">
      <p style="margin-left: 5%">size control</p>
      <el-radio-group v-model="size">
        <el-radio label="large">large</el-radio>
        <el-radio label="default">default</el-radio>
        <el-radio label="small">small</el-radio>
      </el-radio-group>
      <p style="margin-left: 5%">Position Control</p>
      <el-radio-group v-model="labelPosition">
        <el-radio label="left">left</el-radio>
        <el-radio label="right">right</el-radio>
        <el-radio label="top">top</el-radio>
      </el-radio-group>
    </div>
    <br/>
    <el-form
        :size="size"
        :label-position="labelPosition"
        ref="form"
        label-width="auto"
        :model="user"
    >
      <el-form-item label="姓名">
        <el-input v-model="user.user.userName" :model-value="user.user.userName"></el-input>
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="user.user.userPetName" :model-value="user.user.userPetName"></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="user.user.userPassword" :model-value="user.user.userPassword"></el-input>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="user.user.userTel" :model-value="user.user.userTel"></el-input>
      </el-form-item>
      <el-form-item label="地址">
        <el-input v-model="user.user.userAddress" :model-value="user.user.userAddress"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="success" @click="alter()">修改</el-button>
        <el-button type="danger" @click="back()">注销</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import {useRouter} from "vue-router";
import {ref, reactive, onMounted} from "vue";
import axios from "axios";
import {useStore} from "vuex";
let router=useRouter()
let store=useStore();
const size = ref("default")
const labelPosition = ref("left")

const user = reactive({
  user: {
    userName: '',
    userPetName: '',
    userPassword: '',
    userTel: '',
    userAddress: '',
  }
})

console.log(user.user.userName)

onMounted(async () => {
  await axios.get(`api/user?userId=${store.state.userId}`).then(Response => {
    user.user = Response.data
    console.log(user.user)
  })
})

let alter = (() => {
  let alterUser = JSON.stringify(user.user)
  axios.put('api/user', alterUser, {headers: {'Content-Type': 'application/json'}}).then(Response => {
    let message = Response.data
    if (confirm(message)){
      router.push({
        name: 'user',
      })}
  }).catch(Error=>{
    alert(Error.message)
  })
})


let back = (() => {
  console.log("这里是用户自己删除")
  if (confirm("确认注销?")) {
    axios.delete(`api/user?userId=${store.state.userId}`,
    ).then(Response => {
      let message = Response.data
      alert(message)
      store.state.isUser=false
      store.state.userPassword=''
      store.state.userPetName=''
      store.state.userId = ''
      sessionStorage.removeItem('user')
      router.push({
        name: 'book'
      })
    }).catch(Error=>{
      alert(Error.message+"请稍后重试!")
    })
  }
})
</script>

<style scoped>
.el-radio-group {
  margin-right: 12px;
}
.el-input{
  width: 30%;
}
.control{
  float: left;
  width: 30%;
}
.el-form{
  margin-left: 10%;
  float: right;
  width: 60%;
}
.el-button{
  margin-left: 7%;
}
</style>
