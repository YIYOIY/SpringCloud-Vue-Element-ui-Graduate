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
                   style="max-width: 500px" size="large">

            <el-form-item label="用户名" prop="userName">
              <el-input type="text" v-model="people.userName" clearable></el-input>
            </el-form-item>

            <el-form-item label="密码" prop="password">
              <el-input type="password" show-password v-model="people.password" clearable></el-input>
            </el-form-item>

            <el-form-item label="身份识别" prop="type">
              <el-row class="row-bg" justify="space-around" :gutter="30" >
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

                <!--                <el-radio-group v-model="people.userType" title="身份">-->
                <!--                  <el-col :span="8" title="管理员">-->
                <!--                    <el-radio label="admin">管理员</el-radio>-->
                <!--                  </el-col>-->
                <!--                  <el-col :span="8" :offset="8" title="用户">-->
                <!--                    <el-radio label="user">用户</el-radio>-->
                <!--                  </el-col>-->
                <!--                </el-radio-group>-->
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
import {reactive, ref} from "vue";
import {useStore} from "vuex";
import {useRouter} from "vue-router";
import {ElMessage, ElNotification} from "element-plus";
import {adminLogin,userLogin,shopkeeperLogin} from "@/api/LoginApi";

let router = useRouter()
const store = useStore()

const people = reactive({
  userName: '',
  password: '',
  type: ''
})

const rule = reactive({
  userName: [{
    required: true,
    message: '请牢记用户名',
    trigger: 'blur'
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
    trigger: 'blur'
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
        })
      } else if (people.type === 'user') {
        userLogin(user).then(Response => {
          console.log(Response)
          let resp = Response.data
          let mes=Response.message
          window.sessionStorage.setItem("token",Response.token);
          insert(resp,mes)
        })
      }
      else {
        shopkeeperLogin(shopkeeper).then(Response => {
          console.log(Response)
          let resp = Response.data
          let mes=Response.message
          window.sessionStorage.setItem("token",Response.token);
          insert(resp,mes)
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