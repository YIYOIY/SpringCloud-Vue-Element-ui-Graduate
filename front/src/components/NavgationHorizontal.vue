<template>
  <div class="header">
    <el-menu
        class="el-menu-demo"
        mode="horizontal"
        :ellipsis="false"
        default-active="0"
        active-text-color="#ffd04b"
    >

      <el-menu-item index="0">
        <router-link to="/book">
          <el-icon class="is-loading" size="large" style="margin: 0 3px">
            <Guide/>
          </el-icon>
          游弋书城
        </router-link>
      </el-menu-item>

      <el-sub-menu index="9" @mouseover="getSer()">
        <template #title>
          <el-icon class="is-loading" size="large" style="margin: 0 5px">
            <Football/>
          </el-icon>
          系列
        </template>
        <el-menu-item index="9-1" v-for="item in series" :key="item.seriesId">
          <el-tag type="warning" effect="light" @click="selectBySeries(item.seriesName)" style="width: 100%;">
            {{ item.seriesName }}
          </el-tag>
        </el-menu-item>
        <el-menu-item index="1000">
          <el-tag type="success" effect="plain" @click="resBook()" style="width: 100%">全部书籍</el-tag>
        </el-menu-item>
      </el-sub-menu>

      <el-row :gutter="5" class="search">
        <el-col title="按书名查询" :span="14">
          <el-input v-model="searchName" type="text" clearable placeholder="请输入书名"/>
        </el-col>
        <el-col :span="10">
          <el-button type="primary" @click="selectByName" plain round>查询
            <el-icon class="is-loading" size="large" style="margin: 0 3px">
              <Aim/>
            </el-icon>
          </el-button>
        </el-col>
      </el-row>

      <!--用来把其他的导航条往后面靠-->
      <div class="flex-grow"/>


      <el-sub-menu index="2" v-if="store.state.isAdmin">
        <template #title>
          <el-icon class="is-loading" size="large" style="margin: 0 1px">
            <setting/>
          </el-icon>
          <span style="margin-left: 5%">管理员</span>
        </template>
        <el-menu-item-group>
          <template #title>人员管理</template>
          <el-menu-item index="2-0">
            <router-link to="/admin">
              <el-icon>
                <GoldMedal/>
              </el-icon>
              个人信息
            </router-link>
          </el-menu-item>
          <el-menu-item index="2-1">
            <router-link to="/admins">
              <el-icon>
                <Cpu/>
              </el-icon>
              管理员管理
            </router-link>
          </el-menu-item>
          <el-menu-item index="2-2">
            <router-link to="/adminUsers">
              <el-icon>
                <PhoneFilled/>
              </el-icon>
              用户管理
            </router-link>
          </el-menu-item>
          <el-menu-item index="2-3">
            <router-link to="/adminShopkeepers">
              <el-icon>
                <Connection/>
              </el-icon>
              企业管理
            </router-link>
          </el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group title="书籍管理">
          <el-menu-item index="2-4">
            <router-link to="/adminBooks">
              <el-icon>
                <Sunrise/>
              </el-icon>
              书籍信息管理
            </router-link>
          </el-menu-item>
          <el-menu-item index="2-5">
            <router-link to="/series">
              <el-icon>
                <Ship/>
              </el-icon>
              书籍系列管理
            </router-link>
          </el-menu-item>
        </el-menu-item-group>
        <el-sub-menu index="2-6">
          <template #title>用户隐私</template>
          <el-menu-item index="2-6-1">
            <router-link to="/adminOrder">
              <el-icon>
                <ShoppingTrolley/>
              </el-icon>
              购物车管理
            </router-link>
          </el-menu-item>
        </el-sub-menu>
      </el-sub-menu>

      <el-sub-menu index="3" v-if="store.state.isUser">
        <template #title>
          <el-icon class="is-loading" size="large" style="margin: 0 1px">
            <Orange/>
          </el-icon>
          <span style="margin-left: 5%">用户</span>
        </template>
        <el-menu-item-group title="个人中心">
          <el-menu-item index="3-1">
            <router-link to="/user">
              <el-icon>
                <GoldMedal/>
              </el-icon>
              个人信息
            </router-link>
          </el-menu-item>
          <el-menu-item index="3-2">
            <router-link to="/userOrder">
              <el-icon>
                <CreditCard/>
              </el-icon>
              购物车
            </router-link>
          </el-menu-item>
        </el-menu-item-group>
      </el-sub-menu>

      <el-sub-menu index="4" v-if="store.state.isShopkeeper">
        <template #title>
          <el-icon class="is-loading" size="large" style="margin: 0 1px">
            <Orange/>
          </el-icon>
          <span style="margin-left: 5%">企业</span>
        </template>

        <el-menu-item-group title="企业信息">
          <el-menu-item index="4-1">
            <router-link to="/shopkeeper">
              <el-icon>
                <GoldMedal/>
              </el-icon>
              企业信息
            </router-link>
          </el-menu-item>
        </el-menu-item-group>

        <el-menu-item-group title="书籍管理">
          <el-menu-item index="4-2">
            <router-link to="/shopkeeperBooks">
              <el-icon>
                <Sunrise/>
              </el-icon>
              书籍信息管理
            </router-link>
          </el-menu-item>
          <el-menu-item index="4-3">
            <router-link to="/series">
              <el-icon>
                <Ship/>
              </el-icon>
              书籍系列管理
            </router-link>
          </el-menu-item>
        </el-menu-item-group>
        <el-menu-item index="3-2">
          <router-link to="/shopkeeperOrder">
            <el-icon>
              <CreditCard/>
            </el-icon>
            订单管理
          </router-link>
        </el-menu-item>
      </el-sub-menu>


      <el-menu-item index="5" v-if="login">
        <router-link to="/login">
          <el-icon class="is-loading" size="large" style="margin: 0 3px">
            <Sunny/>
          </el-icon>
          登录
        </router-link>
      </el-menu-item>

      <el-menu-item index="6" v-if="login">
        <el-icon class="is-loading" size="large" style="margin: 0 3px">
          <Watermelon/>
        </el-icon>
        <router-link :to="{
              path: '/adduser',
          query: {
            enroll: true
          }
        }
        ">注册
        </router-link>
      </el-menu-item>


      <el-menu-item index="7" v-if="!login">
        <el-icon class="is-loading" size="large" style="margin: 0 3px">
          <Refresh/>
        </el-icon>
        <router-link to="/login">切换账号</router-link>
      </el-menu-item>

      <el-menu-item index="8" v-if="!login">
        <el-icon class="is-loading" size="large" style="margin: 0 3px">
          <Ticket/>
        </el-icon>
        <router-link to="/book" @click="loginout">退出账号</router-link>
      </el-menu-item>

    </el-menu>

  </div>
</template>

<script setup>
import {onBeforeMount, onBeforeUpdate, onMounted, onUnmounted, ref} from "vue";
import {useStore} from "vuex";
import {Football, Refresh, Setting} from '@element-plus/icons-vue'
import {useRouter} from "vue-router";
import {getBooks} from "@/api/BookApi";
import {ElMessage} from "element-plus";
import emitter from "@/utils/bus";
import {getAllSeries} from "@/api/SeriesApi";
const store = useStore()

const router = useRouter()
let login = ref(true)
let a = ref()

// 在导航栏显示书籍系列
let series = ref('')
onBeforeMount(async () => {
  await getAllSeries(null,1,100).then(Response => {
    series.value = Response.data.data
  })
})
// 用来检测管理员是否变动书籍系列
let getSer = (() => {
  getAllSeries(null,1,100).then(Response => {
    series.value = Response.data.data
  })
})

// 触发书籍更新
let selectBySeries = ((v) => {
  emitter.emit('seriesChange', v)
})
let resBook = (() => {
  emitter.emit('tooooBookRestart', "传递成功！")
})


const loginout = () => {
  store.commit('clearState')
  login.value = true
}

onBeforeUpdate(() => {
  if (store.state.isUser || store.state.isAdmin||store.state.isShopkeeper) {
    login.value = false
  } else {
    login.value = true
  }
})

onMounted(() => {
  a = setInterval(() => {
    if (store.state.isUser || store.state.isAdmin||store.state.isShopkeeper) {
      login.value = false
    } else {
      login.value = true
    }
  }, 1000)
})

onUnmounted(() => {
  clearInterval(a)
})


let searchName = ref('')
let id = ref(0)
let selectByName = (() => {
  if (searchName.value === "" || searchName.value === undefined || searchName.value === null) {
    ElMessage.error("请输入书籍名称！")
  } else {
    getBooks(searchName.value,1,10).then(Response => {
      id.value = Response.data.data[0].id
      console.log("查询出来的书籍的id"+id.value)
      GotoBookInfor()
      searchName.value = ''
    }).catch((error) => {
      ElMessage.error(error.data.message)
      searchName.value = ''
    })
  }
})

let GotoBookInfor = (() => {
  router.push({
    path: '/information',
    query: {
      id: id.value
    }
  })
})

</script>

<style scoped>
.header {
  top: 0;
  left: 0;
  width: 100%;
  box-shadow: var(--el-box-shadow-lighter);
}

.search {
  top: 25%;
  left: 20%;
  width: 30%;
  position: absolute;
}

.flex-grow {
  flex-grow: 1;
}

</style>