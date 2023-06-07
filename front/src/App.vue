<template>
  <div class="header">
     <HeaderIndex/>
  </div>
  <div class="navigation">
    <navgation-index/>
  </div>


  <div class="content">
<!--    <router-view></router-view>-->
    <router-view v-slot="{Component,route}">
      <!--        <component :is="Component" :key="route.name" v-if="route.meta.keepAlive"></component>-->
      <keep-alive>
        <component :is="Component" :key="route.name" v-if="route.meta.keepAlive"></component>
      </keep-alive>
      <component :is="Component" :key="route.name" v-if="!route.meta.keepAlive"/>
    </router-view>
  </div>

  <div class="footer">

  </div>
</template>

<script setup>
import NavgationIndex from "@/components/NavgationIndex.vue";
import HeaderIndex from "@/components/HeaderIndex.vue";
</script>

<style>
.header{
  width: 100%;
  height: 10%;
  border-bottom: 2px dashed #d0dde7;
  padding-left: 1%;
}
.navigation{
  float: left;
  width: 12%;
  height: 80%;
  margin-right: 1px;
}
.content{
  float: right;
  width: 85%;
  height: 80%;
  margin: 10px 10px;
  border-left: 2px solid #d0dde7;
  padding-left: 1%;
}
.footer{
  width: 100%;
  height: 10%;
}
a{
  text-decoration-line: none;
}
</style>
