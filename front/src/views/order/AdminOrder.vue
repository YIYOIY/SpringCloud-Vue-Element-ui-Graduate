<template>

  <el-table stripe :data="bags"
            highlight-current-row="true"
            height="600"
            style="width: 100%;margin-top: 3%"
  >
       <el-table-column prop="bagId" label="编号" width="120px"></el-table-column>
    <el-table-column fixed prop="bookPicture" label="封面" width="200px">
      <template v-slot="scope">
        <el-image :src="require('@/assets/static/'+scope.row.bookPicture+'.jpg')"></el-image>
      </template>
    </el-table-column>
    <el-table-column prop="bookName" class-name="bookName" label="书名" width="200px"></el-table-column>
	    <el-table-column prop="userName" label="用户" width="150px"></el-table-column>
	    <el-table-column prop="addTime" label="添加日期" width="150px"></el-table-column>
        <el-table-column prop="bookPrice" label="单价" width="120px"></el-table-column>
	    <el-table-column prop="bookNumber" label="数量" width="120px"></el-table-column>
    <el-table-column prop="bagId" label="操作" width="100px">
      <template v-slot="scope">
        <el-button class="el-button" round color="#626aef" @click="buy(scope.row.bagId)">购买</el-button>
        <el-button class="el-button" round type="danger" @click="del(scope.row.bagId)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-button-group>
	  <el-button @click="pageNoo(pageNo++)" v-show="pageNo<pageTotal">下一页</el-button>
	  <el-button @click="pageNoo(pageNo--)" v-show="pageNo>1">上一页</el-button>
  </el-button-group>
</template>

<script setup>
import {ref} from "vue";
import {useRouter} from "vue-router";
import axios from 'axios'
let bags = ref([])
let pageNo=ref(1);
let pageTotal=ref(1);
let router=useRouter()

axios.get('api/adminPageCount').then(Response => {
  pageTotal.value = Response.data
})
axios.get(`api/adminBag?pageNo=${pageNo.value}`).then(Response => {
  bags.value = Response.data
})

let pageNoo=(()=>{
	axios.get(`api/adminBag?pageNo=${pageNo.value}`).then(Response => {
	  bags.value = Response.data
	})
})

let buy=((v)=>{
console.log("这里是购物车的购买"+v)
 if (confirm("确认购买?")) {
   axios.delete(`api/buybag?bagId=${v}`).then(Response=>{
     let message=Response.data
     alert(message)
     axios.get('api/adminBag').then(Response => {
       bags.value = Response.data
     })
   }).catch(Error=>{
     alert(Error.message+"请稍后重试!")
   })
 }
})

let del=((v)=>{
  if (confirm("确认删除?")) {
    axios.delete(`api/bag?bagId=${v}`).then(Response=>{
      let message=Response.data
      alert(message)
      axios.get('api/adminBag').then(Response => {
        bags.value = Response.data
      })
    }).catch(Error=>{
      alert(Error.message+"请稍后重试!")
    })
  }
})

</script>
<style scoped>
/deep/ .bookName .cell {
  padding-left: 25%;
}
.el-button{
  margin-left: 10%;
  width: 80%;
  margin-top: 2%;
}
</style>
