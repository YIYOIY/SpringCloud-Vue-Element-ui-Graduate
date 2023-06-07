<template>
  <el-form :rules="rule" status-icon :model="people" label-position="center" label-width="20%" style="margin-top: 15%">
    <el-form-item label="用户名" prop="userName" label-width="20%">
      <el-input type="text" v-model="people.userName" style="width: 50%"></el-input>
    </el-form-item>

    <el-form-item label="密码" prop="password" label-width="20%">
      <el-input type="password" v-model="people.password" style="width: 50%"></el-input>
    </el-form-item>

    <el-form-item label="身份" prop="userType" label-width="20%">
      <el-radio-group v-model="people.userType" style="width: 50%">
        <el-radio label="admin" style="margin-left: 20%"></el-radio>
        <el-radio label="user" style="margin-left: 25%"></el-radio>
      </el-radio-group>
    </el-form-item>

    <el-form-item label-width="20%">
      <el-button type="primary" @click="submitForm()" style="width: 25%">提交</el-button>
      <el-button @click="enroll" style="width: 25%">注册</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
import { reactive, ref } from "vue";
import { useStore } from "vuex";
import axios from "axios";
import { useRouter } from "vue-router";
import { onMounted } from "vue";

onMounted(() => {
  // 为了方便编写代码
  store.state.adminPetName = localStorage.getItem('admin').adminPetName
  store.state.adminPassword = localStorage.getItem('admin').adminPassword
  store.state.isAdmin = true
})

let router = useRouter()
const store = useStore()

const people = reactive({
  userName: '',
  password: '',
  userType: ''
})

const rule = reactive({
  userName: [{
    required: true,
    message: '看看你他妈的输入错了没',
    trigger: 'blur'
  }],
  password: [{
    required: true,
    message: '你他妈的密码错了我就弹窗骂你',
    trigger: 'blur'
  }],
  userType: [{
    required: true,
    message: '看看你他妈的是不是管理员',
    trigger: 'change'
  }]
})


const submitForm = () => {
  // 之前用这种不规范的方式传递数据,没有指明请求数据类型
  // let name = people.userName
  // let password = people.password
  // axios.post(`/api/${type}`, {adminPetName: `${name}`, adminPassword: `${password}`}).then(Response => {
  // axios.post(`/api/${type}`, {userPetName: `${name}`, userPassword: `${password}`}).then(Response => {

  if (!people) {
    alert("input something")
    return
  }
  let type = people.userType
  let resp;

  let userdata = {
    userPetName: people.userName,
    userPassword: people.password
  }
  let user = JSON.stringify(userdata)

  let admindata = {
    adminPetName: people.userName,
    adminPassword: people.password
  }
  let admin = JSON.stringify(admindata)


  if (type === 'admin') {
    axios.post(
      `/api/${type}`,
      admin,
      { headers: { 'Content-Type': 'application/json' } }
    ).then(Response => {
      resp = Response.data
      insert(resp)
    })
  } else {
    axios.post(
      `/api/${type}`,
      user,
      { headers: { 'Content-Type': 'application/json' } }
    ).then(Response => {
      resp = Response.data
      insert(resp)
    })
  }

  function insert(resp) {
    if (!resp) {
      alert("用户名或密码错误!你他妈的大傻逼!")
    } else {
      if (type === 'admin') {
        alert('当前给vuex赋值管理员')
        store.state.adminPetName = resp.adminPetName
        store.state.adminPassword = resp.adminPassword
        store.state.isAdmin = true
        router.push({
          path: '/admin'
        })
      } else {
        alert('当前给vuex赋值用户')
        store.state.userPetName = resp.userPetName
        store.state.userPassword = resp.userPassword
        store.state.userId = resp.userId
        store.state.isUser = true
        router.push({
          path: '/user',
        })
      }
    }
  }







}
</script>

<style scoped></style>