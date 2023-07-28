<template>
  <div class="navigation">
    <navgation-index/>
  </div>
  <el-affix :offset="20">
    <el-button type="success" v-if="login">
      <router-link to="/login">
        登录<el-icon class="is-loading" size="small" style="margin: 0px 3px">
        <Compass/>
      </el-icon>
      </router-link>
    </el-button>

    <el-button type="primary" v-if="login">
      <router-link :to="{
          path: '/adduser',
          query: {
            enroll: true
          }
        }
        ">注册
        <el-icon class="is-loading" size="small" style="margin: 0px 3px">
          <promotion/>
        </el-icon>
      </router-link>
    </el-button>

    <el-button type="info" v-if="!login">
      <router-link to="/login">切换账号</router-link>
    </el-button>
    <el-button type="danger" v-if="!login" @click="loginout()">
      <router-link to="/book">退出账号</router-link>
    </el-button>
  </el-affix>

  <h1 :style="{ opacity: opacity }">游 弋 Yoi</h1>

  <el-row :gutter="5" class="search">
    <el-col title="按书名查询" :span="14">
      <el-input v-model="searchName" type="text" clearable placeholder="请输入书名"/>
    </el-col>
    <el-col :span="10">
      <el-button type="primary" @click="selectByName">查询
        <el-icon class="is-loading" size="large" style="margin: 0px 3px">
          <Aim/>
        </el-icon>
      </el-button>
    </el-col>

  </el-row>

</template>

<script setup>
import NavgationIndex from "@/components/NavgationIndex.vue";
import {onBeforeUpdate, onMounted, onUnmounted, ref} from "vue";
import {useStore} from "vuex";
import {useRouter} from "vue-router";
import {Aim, Compass, Food, Help, Promotion} from "@element-plus/icons-vue";
import {getBooksByName} from "@/api/BookApi";

let opacity = ref(1)
let a = ref()
let login = ref(true)

const store = useStore()
const router = useRouter()

const loginout = () => {
  store.state.adminPassword = ''
  store.state.adminName = ''
  store.state.userPassword = ''
  store.state.userName = ''
  store.state.isAdmin = false
  store.state.isUser = false
  sessionStorage.removeItem('token')
  login.value = true
}

onMounted(() => {
  a = setInterval(() => {
    opacity.value -= 0.001
    if (opacity.value <= 0) opacity.value = 1
  }, 1.6)
})

onBeforeUpdate(() => {
  if (store.state.isUser || store.state.isAdmin) {
    login.value = false
  } else {
    login.value = true
  }
})

onUnmounted(() => {
  sessionStorage.removeItem('token')
  sessionStorage.removeItem('store')
  clearInterval(a)
})
let searchName = ref('')
let id = ref(0)
let selectByName = (() => {
  getBooksByName(searchName.value).then(Response => {
    id.value = Response.data[0].bookId
    console.log(id.value)
    back()
    searchName.value=''
  })
})

let back = (() => {
  router.push({
    path: '/information',
    query:{
      id:id.value
    }
  })
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
  width: 20%;
  margin-left: 46%;
  font-size: 35px;
  word-spacing: 10px;
  color: rgba(246, 149, 66, 0.99);
  z-index: 1;
}

.search {
  top: 4%;
  position: absolute;
  left: 10%;
  width: 20%;
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