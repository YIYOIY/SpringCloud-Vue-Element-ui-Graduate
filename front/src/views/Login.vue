<template >
  <div id="building">
    <div class="allbc">
      <el-card class="box-card" shadow="hover">
        <template #header>
          <div class="card-header">
            <span>登录页面</span>
            <el-button class="button" type="success" @click="submitForm" text>登录</el-button>
            <el-button class="button" type="primary" @click="enroll" text>注册</el-button>
          </div>
        </template>

        <template #default>
          <el-form :rules="rule" status-icon :model="people" label-width="100px" label-position="left" ref="form"
            style="max-width: 500px" size="large">
            <el-form-item label="用户名" prop="userName">
              <el-input type="text" v-model="people.userName" clearable></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input type="password" show-password v-model="people.password" clearable></el-input>
            </el-form-item>

            <el-form-item label="身份识别" prop="userType">
              <el-row justify="space-between" :gutter="20">
                <el-radio-group v-model="people.userType" title="身份">
                  <el-col :span="8" title="管理员">
                    <el-radio label="admin">管理员</el-radio>
                  </el-col>
                  <el-col :span="8" :offset="8" title="用户">
                    <el-radio label="user">用户</el-radio>
                  </el-col>
                </el-radio-group>
              </el-row>
            </el-form-item>

            <el-row justify="space-evenly" :gutter="20">
              <el-col :span="8" :offset="6">
                <el-button class="button" type="success" @click="submitForm">登录</el-button>
              </el-col>
              <el-col :span="8">
                <el-button class="button" type="primary" @click="enroll">注册</el-button>
              </el-col>
            </el-row>

          </el-form>
        </template>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from "vue";
import { useStore } from "vuex";
import axios from "axios";
import { useRouter } from "vue-router";
import { onMounted } from "vue";
import { ElMessage } from "element-plus";

// onMounted(() => {
//   // 为了方便编写代码
//   store.state.adminPetName = localStorage.getItem('admin').adminPetName
//   store.state.adminPassword = localStorage.getItem('admin').adminPassword
//   store.state.isAdmin = true
// })

let router = useRouter()
const store = useStore()

const people = reactive({
  userName: '',
  password: '',
  userType: 'user'
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
    trigger: 'change'
  }],
  userType: [{
    required: true,
    message: '看看你他妈的是不是管理员',
    trigger: 'change'
  }]
})


const submitForm = () => {
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
    userName: people.userName,
    userPassword: people.password
  }
  let user = JSON.stringify(userdata)

  let admindata = {
    adminName: people.userName,
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
      ElMessage({
        showClose: true,
        message: '用户名或密码错误!你他妈的大傻逼!',
        type: 'error'
      })
    } else {
      if (type === 'admin') {
        ElMessage({
          showClose: true,
          message: '当前给vuex赋值管理员',
          type: 'success'
        })
        store.state.adminName = resp.adminName
        store.state.adminPassword = resp.adminPassword
        store.state.isAdmin = true
        router.push({
          path: '/book'
        })
      } else {
        ElMessage({
          showClose: true,
          message: '当前给vuex赋值用户',
          type: 'success'
        })
        ElMessage.success('登录成功')
        store.state.userName = resp.userName
        store.state.userPassword = resp.userPassword
        store.state.userId = resp.userId
        store.state.isUser = true
        router.push({
          path: '/book',
        })
      }
    }
  }
}

function enroll() {
  router.push({
    path: '/adduser',
    query: {
      enroll: true
    }
  })
}
</script>
<style scoped>
.card-header {
  opacity: 0.6;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.el-col {
  border-radius: 4px;
}

#building {
  background: url(../assets/static/123.png);
  width: 100%;
  height: 100%;
  font-size: large;
  position: fixed;
  background-size: 90% 70%;
  background-repeat: no-repeat;
  background-attachment: fixed;
  background-position: center;
  z-index: -1;
}

.allbc {
  margin: 5% 30%;
  z-index: 1;
  opacity: 0.9;
}
</style>