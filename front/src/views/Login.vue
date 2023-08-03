<template>
  <div id="building">
    <div class="allbc">

      <el-card class="box-card" shadow="hover">
        <template #header>
          <div class="card-header">Sunsetting Yoi Store</div>
        </template>

        <template #default>
          <el-form :rules="rule" status-icon :model="people" label-position="top" ref="form" class="formSub"
                   :inline="true">
            <el-form-item label="用户名" prop="userName" style="max-width: 60%">
              <el-input type="text" v-model="people.userName" clearable></el-input>
            </el-form-item>

            <el-form-item label="密码" prop="password" style="max-width: 60%">
              <el-input type="password" show-password v-model="people.password" clearable></el-input>
            </el-form-item>

            <el-form-item label="身份识别" prop="type" class="formType">
              <el-row justify="space-evenly" style="width: 100%">
                <el-col :span="6" :offset="2">
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

                <el-col :span="6">
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

                <el-col :span="8">
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
          </el-form>
          <el-row justify="space-evenly" style="width: 100%;margin: 5% 5% 0 5%;">
            <el-col :span="10">
              <el-button style="width: 70%;font-size: 20px;color: #26850c" size="large" type="success" @click="submitForm" plain round>登录</el-button>
            </el-col>
            <el-col :span="10">
              <el-button style="width: 70%;font-size: 20px;color: #8d1417" size="large" type="danger" @click="enroll" plain round>注册</el-button>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="24">
              <div style="width: 100%;margin:3% 1%">
                <el-steps :active="active" align-center finish-status="success">
                  <el-step title="Step 1" description="输入用户名"/>
                  <el-step title="Step 2" description="输入密码"/>
                  <el-step title="Step 3" description="选择身份"/>
                  <el-step title="Step 4" description="登录！"/>
                </el-steps>
              </div>
            </el-col>
          </el-row>

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
import {adminLogin, userLogin, shopkeeperLogin} from "@/api/LoginApi";

let active = ref(0)
let router = useRouter()
const store = useStore()
onMounted(() => {
  tip()
})
let tip = (() => {
  setInterval(() => {
    active.value++ > 3 ? active.value = 0 : active.value
  }, 2000)
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
    value: true,
    message: '请选择登陆渠道',
    trigger: 'blur',
  }]
})

let form = ref('')
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
        let mes = Response.message
        window.sessionStorage.setItem("token", Response.token);
        insert(resp, mes)
      }).catch(error => {
        console.log(error)
        ElMessage.error(error.data.message)
      })
    } else if (people.type === 'user') {
      userLogin(user).then(Response => {
        console.log(Response)
        let resp = Response.data
        let mes = Response.message
        window.sessionStorage.setItem("token", Response.token);
        insert(resp, mes)
      }).catch(error => {
        console.log(error)
        ElMessage.error(error.data.message)
      })
    } else {
      shopkeeperLogin(shopkeeper).then(Response => {
        console.log(Response)
        let resp = Response.data
        let mes = Response.message
        window.sessionStorage.setItem("token", Response.token);
        insert(resp, mes)
      }).catch(error => {
        console.log(error)
        ElMessage.error(error.data.message)
      })
    }
  })

  function insert(resp, mes) {
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
      } else if (people.type === 'user') {
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
      } else {
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
.formSub {
  margin: 1% 10%;
  max-width: 100%;
  padding: 2% 5%;
  background-color: rgb(255, 255, 255);
  border-radius: 40px;
  box-shadow: inset 0 -3em 3em #d3d9de,0 0 10px rgb(255, 255, 255),1em 1em 3em 1em #1d5f98;
}

/deep/ .el-form-item {
  margin: 4% 6%;
  width: 35%;
  font-size: 15px;
  background-color: rgb(252, 252, 252);
  box-shadow: -1em 1em 2em 1em #d3d9de;
  border-radius: 10px;
}

/deep/ .el-form-item__label {
  margin: 4% 2%;
  width: 60%;
  font-size: 15px;
  color: #5a6557;
  border-radius: 8px;

}

/deep/ .el-input__inner {
  max-width: 95%;
  border-radius: 6px;
  box-shadow: 2.3em 0 0.1em #f1f2f5;
}

/deep/ .el-input__wrapper {
  max-width: 95%;
  border-radius: 6px;
}

.formType {
  width: 100%;
}

.el-col {
  border-radius: 4px;
}

.card-header {
  display: flex;
  color: #252525;
  font-size: 40px;
  text-shadow: 2px 2px 1px #ffb011;
  justify-content: space-around;
  align-items: center;
  border-radius: 5px;
  box-shadow: inset 0 0 1px rgb(54, 102, 170),0.2em 0.2em 0.8em 0.7em rgb(217, 217, 221);
  width: 60%;
}

/deep/ .el-card__body {
  box-shadow: 1em 1em 4em rgba(0,0,0,0.3);
  border-radius: 20px;
  background-color: rgba(96, 138, 183, 0.12);
}

#building {
  top: 0;
  left: 0;
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
  width: 50%;
  height: 70%;
  margin: 5% 25%;
  z-index: 1;
}
</style>