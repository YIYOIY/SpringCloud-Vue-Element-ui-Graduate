<template>
  <p v-show="false">{{ book.book.bookName }}</p>
  <div style="margin: 5% 2%;text-align: center;align-content: center">
    <div style="width: 50%;float: left">
    <el-form
        size="default"
        label-position="left"
        ref="form"
        label-width="auto"
        :model="book"
    >
<!--      唯一编号,不作为修改属性-->
<!--      <el-form-item label="编号">-->
<!--        <el-input v-model="book.book.bookId" :model-value="book.book.bookId"></el-input>-->
<!--      </el-form-item>-->
      <el-form-item label="编号">
        <el-input v-model="book.book.bookNo" :model-value="book.book.bookNo"></el-input>
      </el-form-item>
      <el-form-item label="书名">
        <el-input v-model="book.book.bookName" :model-value="book.book.bookName"></el-input>
      </el-form-item>
      <el-form-item label="作者">
        <el-input v-model="book.book.bookAuthor" :model-value="book.book.bookAuthor"></el-input>
      </el-form-item>
      <el-form-item label="价格">
        <el-input v-model="book.book.bookPrice" :model-value="book.book.bookPrice"></el-input>
      </el-form-item>
      <el-form-item label="系列">
        <el-input v-model="book.book.seriesName" :model-value="book.book.seriesName"></el-input>
      </el-form-item>
      <el-form-item label="出版社">
        <el-input v-model="book.book.bookFactory" :model-value="book.book.bookFactory"></el-input>
      </el-form-item>
      <el-form-item label="发售日期">
        <el-input v-model="book.book.bookAddDate" :model-value="book.book.bookAddDate"></el-input>
      </el-form-item>
      <el-form-item label="封面">
        <el-input v-model="book.book.bookPicture" :model-value="book.book.bookPicture"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="danger" @click="alter()">修改</el-button>
        <el-button type="sucess" @click="back()">返回</el-button>
      </el-form-item>
    </el-form>
    </div>


    <div style="width: 40%;float: right">
      <el-image   :src="require('@/assets/static/'+book.book.bookPicture +'.jpg')"></el-image>
      <el-upload
          class="upload-demo"
          drag
          action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
          multiple
          limit="1"
      >
        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
        <div class="el-upload__text">
          Drop file here or <em>click to upload</em>
        </div>
        <template #tip>
          <div class="el-upload__tip">
            jpg/png files with a size less than 500kb
          </div>
        </template>
      </el-upload>
    </div>

  </div>
</template>

<script setup>
import {useRouter} from "vue-router";
import {reactive, onBeforeMount, toRef} from "vue";
import axios from "axios";
import { UploadFilled } from '@element-plus/icons-vue'
let router=useRouter()

const prop = defineProps(['bookId'])
let bookId=toRef(prop,'bookId')
console.log(bookId.value+"!!!!!!!!!!!!!!!!!!!!!!")

const book = reactive({
  book: {
    bookName: "",
    bookAddDate: "",
    bookAuthor: "",
    bookFactory: "",
    bookId: "",
    bookNo: "",
    bookPicture: "",
    bookPrice: "",
    seriesName: "",
  }
})



onBeforeMount(async () => {
  await axios.get(`api/lookup?bookId=${bookId.value}`).then(Response => {
    book.book = Response.data
    console.log(book.book.bookName+"@@@@@@@@@@@@@@@@@@@@@@@@@@@")
  })
})


let alter = (() => {
  let alterBook = JSON.stringify(book.book)

  axios.put('api/book', alterBook, {headers: {'Content-Type': 'application/json'}}).then(Response => {
    let message = Response.data
    if (confirm(message+"!  是否跳转到书籍页")){
      router.push({
        name: 'adminBooks',
      })}
  }).catch(Error=>{
    alert(Error.message)
  })
})


let back = (() => {
  router.push({
    name: 'adminBooks'
  })
})
</script>

<style scoped>
.el-radio-group {
  margin-right: 12px;
}
.el-input{
  width: 30%;
}
.control{
  float: left;
  width: 30%;
}
.el-form{
  margin-left: 10%;
  float: right;
 width: 60%;
}
</style>
