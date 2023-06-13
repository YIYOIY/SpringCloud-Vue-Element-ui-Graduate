<template>
  <div class="navigation">
    <navgation-index />
  </div>

  <el-affix :offset="20">



    <el-button type="success" v-if="login">
      <router-link to="/login">登录</router-link>
    </el-button>

    <el-button type="primary" v-if="login">
      <router-link :to="{
          path: '/adduser',
          query: {
            enroll: true
          }
        }
        ">注册
      </router-link>
    </el-button>

    <el-button type="info" v-if="!login">
      <router-link to="/login">切换账号</router-link>
    </el-button>
    <el-button type="danger" v-if="!login" @click="loginout()">
      <router-link to="/login">退出账号</router-link>
    </el-button>
  </el-affix>
  <h1 :style="{ opacity: opacity }">路上捡的书<sub>买不买不买滚</sub></h1>
</template>

<script setup>
import NavgationIndex from "@/components/NavgationIndex.vue";
import { onBeforeUpdate, onMounted, onUnmounted, ref } from "vue";
import { useStore } from "vuex";

let opacity = ref(1)
let a = ref()
let login = ref(true)

const store = useStore()
const loginout = () => {
  sessionStorage.removeItem('user')
  sessionStorage.removeItem('admin')
  store.state.userPassword = ''
  store.state.userPetName = ''
  store.state.userId = ''
  store.state.adminPassword = ''
  store.state.adminPetName = ''
  store.state.isAdmin = false
  store.state.isUser = false
  login.value = true
}

onMounted(() => {
  a = setInterval(() => {
    opacity.value -= 0.001
    if (opacity.value <= 0) opacity.value = 1
  }, 1.6)
})

onBeforeUpdate(() => {
  if ((sessionStorage.getItem('admin') || sessionStorage.getItem('user')) && (store.state.isUser || store.state.isAdmin)) {
    login.value = false
  } else {
    login.value = true
  }
})

onUnmounted(() => {
  sessionStorage.removeItem('user')
  sessionStorage.removeItem('admin')
  clearInterval(a)
})
</script>

<style scoped>
.navigation {
  position: fixed;
  float: left;
  width: 13%;
  z-index: 2;
}

h1 {
  width: 40%;
  margin-left: 36%;
  color: #cc6b7a;
}

.el-button {
  float: right;
  margin-right: 5%;
}

a {
  color: snow;
  text-decoration-line: none;
}
</style>