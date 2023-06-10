<template>
  <!--  <el-radio-group v-model="isCollapse"  @mouseover="change()">-->
  <!--    <el-radio-button :label="true"  class="radio">折叠</el-radio-button>-->
  <!--    <el-radio-button :label="false" class="radio">展开</el-radio-button>-->
  <!--  </el-radio-group>-->
  <el-menu default-active="2" class="el-menu-vertical-demo" :collapse="isCollapse" @mouseenter="enter"
    @mouseleave="leave">

    <el-sub-menu index="1">
      <template #title>
        <el-icon>
          <HomeFilled />
        </el-icon>
        <span style="margin-left: 5%">书城</span>
      </template>
      <el-menu-item-group>
        <template #title>上新中......</template>
        <el-menu-item index="1-1"><router-link to="/book" active-class="active-class"
            exact-active-class="exact-active">书城首页</router-link></el-menu-item>
        <!-- <el-menu-item index="1-2"><router-link to="/book" active-class="active-class" exact-active-class="exact-active">书城分类</router-link></el-menu-item> -->
      </el-menu-item-group>
    </el-sub-menu>


    <el-sub-menu index="2" v-show="isAdmin">
      <template #title>
        <el-icon>
          <Location />
        </el-icon>
        <span style="margin-left: 5%">管理员</span>
      </template>

      <el-menu-item-group>
        <template #title>人员管理</template>
        <el-menu-item index="2-1"><router-link to="/admin" active-class="active-class"
            exact-active-class="exact-active">管理员管理</router-link></el-menu-item>
        <el-menu-item index="2-2"><router-link to="/adminUsers" active-class="active-class"
            exact-active-class="exact-active">用户管理</router-link></el-menu-item>
      </el-menu-item-group>

      <el-menu-item-group title="书籍管理">
        <el-menu-item index="2-3"><router-link to="/adminBooks" active-class="active-class"
            exact-active-class="exact-active">书籍信息管理</router-link></el-menu-item>
        <el-menu-item index="2-3"><router-link to="/category" active-class="active-class"
            exact-active-class="exact-active">书籍类型管理</router-link></el-menu-item>
      </el-menu-item-group>

      <el-sub-menu index="2-4">
        <template #title>用户隐私</template>
        <el-menu-item index="2-4-1"><router-link to="/adminOrder" active-class="active-class"
            exact-active-class="exact-active">购物车管理</router-link></el-menu-item>
      </el-sub-menu>
    </el-sub-menu>

    <el-sub-menu index="3" v-show="isUser">
      <template #title>
        <el-icon>
          <setting />
        </el-icon>
        <span style="margin-left: 5%">用户</span>
      </template>
      <el-menu-item-group>
        <template #title>个人中心</template>
        <el-menu-item index="3-1"><router-link to="/user" active-class="active-class"
            exact-active-class="exact-active">用户信息</router-link></el-menu-item>
        <el-menu-item index="3-2"><router-link to="/userOrder" active-class="active-class"
            exact-active-class="exact-active">购物车</router-link></el-menu-item>
      </el-menu-item-group>
    </el-sub-menu>

    <!--    <el-menu-item index="4">-->
    <!--      <el-icon><Document/></el-icon>-->
    <!--      <template #title>three</template>-->
    <!--    </el-menu-item>-->

    <!--    <el-menu-item index="5">-->
    <!--      <el-icon><IconMenu/></el-icon>-->
    <!--      <template #title>four</template>-->
    <!--    </el-menu-item>-->

  </el-menu>
</template>

<script setup>
import { ref } from "vue";
import { useStore } from "vuex";
import { Location, Setting, Menu as IconMenu, Document, HomeFilled } from '@element-plus/icons-vue'
import { useRoute } from "vue-router";
const store = useStore()
const isCollapse = ref(true)
let route = useRoute()
const isAdmin = ref(false)
const isUser = ref(false)
const enter = (() => {
  console.log(store.state.isAdmine + '这里是navigation的判断是否是管理员,或者用户')
  isCollapse.value = false
  isAdmin.value = store.state.isAdmin
  isUser.value = store.state.isUser
})

const leave = (() => {
  isCollapse.value = true
  console.log(store.state.isAdmine + '这里是navigation的判断是否是管理员,或者用户')
  isAdmin.value = store.state.isAdmin
  isUser.value = store.state.isUser
})

// onBeforeUpdate(()=>{
//   console.log(store.state.isAdmine+'这里是navigation的判断是否是管理员')
//   isAdmin.value =store.state.isAdmin
// })

</script>

<style scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 100%;
  min-height: 80%;
}

.active {
  color: #20eaea;
}

.exact-active {
  color: #10ea7f;
}

.radio {
  align-content: center;
  margin: 2% 5%;
  width: 40%;
}

.el-menu-item .el-icon {
  margin-right: 5%;
}

.el-sub-menu .el-menu-item {
  margin-left: 10%;
}
</style>