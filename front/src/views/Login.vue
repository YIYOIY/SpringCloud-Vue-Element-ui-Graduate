<template>
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
          <el-form :rules="rule" status-icon :model="people" label-width="120px" label-position="left" ref="form"
                   style="max-width: 600px" size="large">
            <el-form-item label="用户名" prop="userName">
              <el-input type="text" v-model="people.userName" clearable></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input type="password" show-password v-model="people.password" clearable></el-input>
            </el-form-item>
            <el-form-item label="身份识别" prop="type">
              <el-row class="row-bg" justify="space-around" :gutter="100" >
                <el-col :span="6">
                  <el-switch
                      class="ml-2"
                      size="large"
                      name="用户"
                      v-model="people.type"
                      inline-prompt
                      style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
                      active-value="user"
                      active-text="用户"
                      inactive-text="用户"
                  />
                </el-col>

                <el-col :span="6" >
                  <el-switch
                      class="ml-2"
                      size="large"
                      name="管理员"
                      v-model="people.type"
                      inline-prompt
                      style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
                      active-value="admin"
                      active-text="管理员"
                      inactive-text="管理员"
                  />
                </el-col>

                <el-col :span="6">
                  <el-switch
                      class="ml-2"
                      size="large"
                      name="商家"
                      v-model="people.type"
                      inline-prompt
                      style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
                      active-value="shopkeeper"
                      active-text="商家"
                      inactive-text="商家"
                  />
                </el-col>
              </el-row>
            </el-form-item>
            <el-row justify="space-evenly" :gutter="20">
              <el-col :span="8" :offset="8">
                <el-button class="button" type="success" @click="submitForm" plain round>登录</el-button>
              </el-col>
              <el-col :span="8">
                <el-button class="button" type="primary" @click="enroll" plain round>注册</el-button>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <div style="width: 100%;margin:3% 10%">
                  <el-steps :active="active" align-center finish-status="success" >
                    <el-step title="Step 1" description="输入用户名" />
                    <el-step title="Step 2" description="输入密码" />
                    <el-step title="Step 3" description="选择身份" />
                    <el-step title="Step 4" description="登录！" />
                  </el-steps>
                </div>
              </el-col>
            </el-row>
          </el-form>
        </template>
      </el-card>
    </div>
  </div>

</template>

<script setup>
import {onMounted, reactive, ref} from "vue";
import {useStore} from "vuex";
import {useRouter} from "vue-router";
import {ElMessage, ElNotification} from "element-plus";
import {adminLogin,userLogin,shopkeeperLogin} from "@/api/LoginApi";
let active=ref(0)
let router = useRouter()
const store = useStore()
onMounted(()=>{
  tip()
})
let tip=(()=>{
  setInterval(()=>{
    active.value++>3?active.value=0:active.value
  },2000)
})
const people = reactive({
  userName: '',
  password: '',
  type: ''
})

const rule = reactive({
  userName: [{
    required: true,
    message: '请牢记用户名',
    trigger: 'blur',
  }],
  password: [{
    required: true,
    message: '密码谨防泄露',
    trigger: 'blur'
  }],
  type: [{
    required: true,
    value:true,
    message: '请选择登陆渠道',
    trigger: 'blur',
  }]
})

let form=ref('')
const submitForm = () => {
  form.value.validate(valid => {
    if (!valid) {
      ElNotification({
        title: '警告',
        message: '请检查输入的内容是否正确',
        type: 'warning',
        position: 'top-left'
      })
      return false
    }

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

      let shopkeeperdata = {
        shopkeeperName: people.userName,
        shopkeeperPassword: people.password
      }
      let shopkeeper = JSON.stringify(shopkeeperdata)
      if (people.type === 'admin') {
        adminLogin(admin).then(Response => {
          // 在axios 的封装中已经剥开一次res.data了所以在这里已经是returninfo的返回值，在这里还要拿到data就是具体的data封装的对象了
          console.log(Response)
          let resp = Response.data
          let mes=Response.message
          window.sessionStorage.setItem("token",Response.token);
          insert(resp,mes)
        }).catch(error=>{
          console.log(error)
          ElMessage.error(error.data.message)
        })
      } else if (people.type === 'user') {
        userLogin(user).then(Response => {
          console.log(Response)
          let resp = Response.data
          let mes=Response.message
          window.sessionStorage.setItem("token",Response.token);
          insert(resp,mes)
        }).catch(error=>{
          console.log(error)
          ElMessage.error(error.data.message)
        })
      }
      else {
        shopkeeperLogin(shopkeeper).then(Response => {
          console.log(Response)
          let resp = Response.data
          let mes=Response.message
          window.sessionStorage.setItem("token",Response.token);
          insert(resp,mes)
        }).catch(error=>{
          console.log(error)
          ElMessage.error(error.data.message)
        })
      }
  })

  function insert(resp,mes) {
    if (!resp) {
      ElMessage({
        showClose: true,
        message: mes,
        type: 'error'
      })
    } else {
      if (people.type === 'admin') {
        ElMessage({
          showClose: true,
          message: mes,
          type: 'success'
        })
        store.state.adminName = resp.adminName
        store.state.adminId = resp.adminId
        store.state.adminPassword = resp.adminPassword
        store.state.isAdmin = true
        router.push({
          path: '/book'
        })
      }
      else if (people.type==='user'){
        ElMessage({
          showClose: true,
          message: mes,
          type: 'success'
        })
        store.state.userName = resp.userName
        store.state.userPassword = resp.userPassword
        store.state.userId = resp.userId
        store.state.isUser = true
        router.push({
          path: '/book',
        })
      }
      else {
        ElMessage({
          showClose: true,
          message: mes,
          type: 'success'
        })
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
  background: url(../assets/static/login.png);
  width: 100%;
  height: 100%;
  font-size: 20px;
  position: fixed;
  background-size: 100% 100%;
  background-repeat: no-repeat;
  background-attachment: local;
  background-position: left;
  z-index: -1;
}

.allbc {
  margin: 5% 25%;
  z-index: 1;
  opacity: 0.9;
}
</style>