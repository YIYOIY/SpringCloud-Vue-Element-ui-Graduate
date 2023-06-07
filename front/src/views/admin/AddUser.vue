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
        <el-button type="sucess" @click="add()">新增</el-button>
        <el-button type="primary" @click="cancelAdd()">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import {ref, reactive} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";
import {useRoute} from "vue-router";
const size = ref("default")
const labelPosition = ref("left")
let router = useRouter()
let route = useRoute()

let isEnroll=ref(false)
if (route.query.enroll){
  isEnroll.value=true
}
console.log(isEnroll.value)

const user = reactive({
  user: {
    userName: '',
    userPetName: '',
    userPassword: '',
    userTel: '',
    userAddress: '',
  }
})

const add=(()=>{
  let addUser =JSON.stringify(user.user)
  console.log(addUser)
  axios.post('api/addUser', addUser, {headers: {'Content-Type': 'application/json'}}).then(Response => {
    let message = Response.data
    if (isEnroll.value){
      alert("注册成功!")
      router.push('/login')
    }else{
    if (confirm(message+"是否跳转到用户首页?")) {
      router.push('/adminUsers')
    }
    }
  }).catch(Error=>{
    alert(Error.message+"添加失败,请稍后重试!")
  })
})

const cancelAdd=(()=>{
  router.push('/adminUsers')
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
</style>