<template>
  <p v-show="false">{{ book.book.bookName }}</p>
  <div style="margin: 3% 2%;text-align: center;align-content: center">
    <div style="width: 50%;float: left;margin: 5% 1%">
      <el-form size="default" label-position="left" ref="form" label-width="100px" :model="book">
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
        <el-form-item>
          <div class="block">
            <span class="demonstration" style="float: left">发售日期</span>
            <div class="demonstration">{{ book.book.bookAddDate }}</div>
            <el-date-picker v-model="book.book.bookAddDate" type="date" placeholder="Pick a Date" format="YYYY/MM/DD"
              value-format="YYYY-MM-DD" />
          </div>
          <!--          <el-input v-model="book.book.bookAddDate" :model-value="book.book.bookAddDate"></el-input>-->
        </el-form-item>
        <!-- <el-form-item label="封面">
          <el-input v-model="book.book.bookPicture" :model-value="book.book.bookPicture"></el-input>
        </el-form-item> -->
        <el-form-item>
          <el-button type="danger" @click="add()">添加</el-button>
          <el-button type="sucess" @click="back()">返回</el-button>
        </el-form-item>
      </el-form>
    </div>



    <div style="width: 40%;float: right;margin-right: 7%">
      <img :src='PICTURE' style="width:100%;height:50%" v-show="havePicture" alt="上传的图片" />
      <el-button @click="havePicture = !havePicture" type="primary" v-show="havePicture">重新上传图片</el-button>
      <el-button type="warning" v-show="havePicture"><a href="api/test/download">测试下载</a></el-button>

      <el-upload class=" upload-demo" drag action="api/test/up" multiple limit="100" encytype="multipart/form-data"
        ref="pict" name="photo" v-show="!havePicture" @keydown.y="handleBookPicture()">
        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
        <div class="el-upload__text">
          <em>图片拖入</em> 或<em>点击上传</em>
          <p> y键可查看上传的图片</p>
          重新上传图片请先清空列表
        </div>
        <template #tip>
          <div class="el-upload__tip">
            只能上传图片
          </div>
        </template>
      </el-upload>
    </div>

  </div>
</template>

<script setup>
import { useRouter } from "vue-router";
import { reactive, onBeforeMount, toRef, ref } from "vue";
import { ElForm, ElFormItem, ElInput, ElButton, ElDatePicker, ElUpload, ElIcon } from "element-plus";
import axios from "axios";
let router = useRouter()

// 下面提供了一个虚假的bookId用于反射json数据到java类,不然会因为id为空而反射失败报错,在插入数据时不会插入这个id,此id仅作为postmapping接收axios请求反射book实例而存在
const book = reactive({
  book: {
    bookName: "",
    bookAddDate: "",
    bookAuthor: "",
    bookFactory: "",
    bookId: "1",
    bookNo: "",
    bookPicture: "",
    bookPrice: "",
    seriesName: "",
  }
})



let PICTURE = ref('');
let havePicture = ref(false);

let pict = ref('')
let handleBookPicture = (() => {
  pict.value.clearFiles()

  setTimeout(() => {
    axios.get('api/getPicture').then(Response => {
      PICTURE.value = Response.data
      if (PICTURE.value != null && PICTURE.value != "")
        havePicture.value = true
      // alert(Response.data)
    }).catch(Error => {
      alert(Error.message)
    })
  }, 500);
})


let add = (() => {
  let addBook = JSON.stringify(book.book)

  axios.post('api/book', addBook, { headers: { 'Content-Type': 'application/json' } }).then(Response => {
    let message = Response.data
    if (confirm(message + "!  是否跳转到书籍页")) {
      router.push({
        name: 'adminBooks',
      })
    }
  }).catch(Error => {
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

.el-input {
  width: 80%;
}

.el-form {
  margin-left: 70%;
  float: right;
  width: 100%;
}
</style>
