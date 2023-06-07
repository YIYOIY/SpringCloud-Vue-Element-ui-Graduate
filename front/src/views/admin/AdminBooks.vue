<template>
  <el-button @click="add()" type="info" style="margin-top: 1%">添加新书籍</el-button>
  <el-table stripe :data="books"
            highlight-current-row="true"
            height="600"
            style="width: 100%;margin-top: 3%"
  >
    <!--    <el-table-column prop="bookNo" label="书籍编号" width="120px"></el-table-column>-->
    <el-table-column fixed prop="bookPicture" label="封面" width="200px">
      <template v-slot="scope">
        <el-image :src="require('@/assets/static/'+scope.row.bookPicture+'.jpg')"></el-image>
      </template>
    </el-table-column>
    <el-table-column prop="bookName" class-name="bookName" label="书名" width="200px"></el-table-column>
    <el-table-column prop="bookAuthor" label="作者" width="150px"></el-table-column>
    <el-table-column prop="seriesName" label="系列" width="150px"></el-table-column>
    <el-table-column prop="bookPrice" label="价格" width="120px"></el-table-column>
    <el-table-column prop="bookAddDate" label="发布日期" width="150px"></el-table-column>
    <el-table-column prop="bookFactory" label="出版社" width="120px"></el-table-column>
    <el-table-column prop="bookId" label="操作" width="100px">
      <template v-slot="scope">
        <el-button class="el-button" round color="#626aef" @click="alter(scope.row.bookId)">修改</el-button>
        <el-button class="el-button" round type="danger" @click="del(scope.row.bookId)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>
<script setup>
import {ref} from "vue";
import {useRouter} from "vue-router";
import axios from 'axios'
let books = ref([])
let router=useRouter()

axios.get('api/book').then(Response => {
  books.value = Response.data
})

let alter=((v)=>{
console.log("这里是管理书籍的准备进入修改书籍"+v)
  let id=v
  router.push({
    name:'alterBooks',
    query:{
      bookId:id
    }
  })
})

let del=((v)=>{
  if (confirm("确认删除?")) {
    axios.delete(`api/book?bookId=${v}`).then(Response=>{
      let message=Response.data
      alert(message)
      axios.get('api/book').then(Response => {
        books.value = Response.data
      })
    }).catch(Error=>{
      alert(Error.message+"请稍后重试!")
    })
  }
})

const add=(()=>{
  router.push({
    name: 'addBook'
  })
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
