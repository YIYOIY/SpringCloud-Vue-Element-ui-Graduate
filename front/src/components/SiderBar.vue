<template>
  <p  class="overArea" @mouseover="drawer = true">网站导航</p>
  <div class="navigation" >
    <el-drawer v-model="drawer" :show-close="false" direction="ltr">
      <template #header="{close}">
        <el-button type="danger" @click="close"  plain round>
          <el-icon class="el-icon--left">
            <CircleCloseFilled/>
          </el-icon>
          Close
        </el-button>
        <el-button type="primary" v-if="login" round>
          <router-link to="/login">
            登录
            <el-icon class="is-loading" size="small" style="margin: 0px 3px">
              <Compass/>
            </el-icon>
          </router-link>
        </el-button>

        <el-button type="success" v-if="login" round>
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

        <el-button type="info" v-if="!login" round>
          <el-icon class="is-loading" size="large" style="margin: 0px 3px">
            <Refresh/>
          </el-icon>
          <router-link to="/login">切换账号</router-link>
        </el-button>
        <el-button type="danger" v-if="!login" @click="loginout()" round>
          <el-icon class="is-loading" size="large" style="margin: 0px 3px">
            <Ticket />
          </el-icon>
          <router-link to="/book">退出账号</router-link>
        </el-button>
      </template>

      <el-row :gutter="5" class="search" style="z-index: 2">
        <el-col title="按书名查询" :span="14">
          <el-input v-model="searchName" type="text" clearable placeholder="请输入书名"/>
        </el-col>
        <el-col :span="10">
          <el-button type="primary" @click="selectByName" plain round>查询
            <el-icon class="is-loading" size="large" style="margin: 0px 3px">
              <Aim/>
            </el-icon>
          </el-button>
        </el-col>
      </el-row>

      <ElDivider content-position="center">Hi Yoi's BookShop! Awesome Books !</ElDivider>

      <SiderBarNavigation/>

      <el-button type="warning" plain round style="margin-left: 1px" @click="store.state.menu=1">切换菜单</el-button>
    </el-drawer>
 </div>
</template>

<script setup>
import SiderBarNavigation from "@/components/SiderBarNavigation.vue";
import {onBeforeUpdate, onMounted, onUnmounted, ref} from "vue";
import {useStore} from "vuex";
import {useRouter} from "vue-router";
import {Aim, Compass, Promotion, Refresh} from "@element-plus/icons-vue";
import {getBooksByName} from "@/api/BookApi";
import {ElMessage} from "element-plus";
const store = useStore()
const router = useRouter()

const drawer = ref(false)
let a = ref()

let login = ref(true)

const loginout = () => {
  store.state.adminPassword = ''
  store.state.adminName = ''
  store.state.adminId=''
  store.state.userPassword = ''
  store.state.userName = ''
  store.state.userId=''
  store.state.isAdmin = false
  store.state.isUser = false
  sessionStorage.removeItem('token')
  sessionStorage.removeItem('store')
  login.value = true
}

onBeforeUpdate(() => {
  if (store.state.isUser || store.state.isAdmin) {
    login.value = false
  } else {
    login.value = true
  }
})

onMounted(() => {
  a = setInterval(() => {
    if (store.state.isUser || store.state.isAdmin) {
      login.value = false
    } else {
      login.value = true
    }
  }, 1000)
})

onUnmounted(() => {
  sessionStorage.removeItem('token')
  sessionStorage.removeItem('store')
  clearInterval(a)
})
let searchName = ref('')
let id = ref(0)
let selectByName = (() => {
  if (searchName.value === "" || searchName.value === undefined || searchName.value === null) {
    ElMessage.error("请输入书籍名称！")
  } else {
  getBooksByName(searchName.value).then(Response => {
    id.value = Response.data[0].bookId
    console.log(id.value)
    back()
    searchName.value = ''
  }).catch((error) => {
    ElMessage.error(error.data.message)
    searchName.value = ''
  })
  }
})

let back = (() => {
  router.push({
    path: '/information',
    query: {
      id: id.value
    }
  })
})
</script>

<style scoped>
.navigation {
  top: 0;
  left: 0;
  width: 100%;
}

.overArea{
  opacity: 0;
  width: 5%;
  height: 100%;
  background-color: chocolate;
  position: fixed;
  z-index: 2;
}

.search {
  top: 10%;
  left: 10%;
  width: 80%;
  position: absolute;
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