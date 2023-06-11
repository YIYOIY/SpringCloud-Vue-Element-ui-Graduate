<template>
  <!-- <p v-show="false">{{ book.book.bookName }}</p> -->
  <div style="margin: 5% 2%">

    <el-form size="default" label-position="left" ref="form" width="100%" :model="book">
      <div style="width: 55%;float: left">
        <el-row gutter="2" justify="space-evenly">
          <el-col span="10">
            <el-form-item label="编号">
              <el-input v-model="book.book.bookId" :model-value="book.book.bookId" disabled></el-input>
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
          </el-col>
          <el-col :span="12">
            <el-form-item label="系列">
              <el-select v-model="book.book.seriesName" :model-value="book.book.seriesName" placeholder="请选择">
                <el-option v-for="item in series" :key="item.seriesId" :label="item.seriesName"
                  :value="item.seriesName"></el-option>
              </el-select>
              <!-- <el-input v-model="book.book.seriesName" :model-value="book.book.seriesName"></el-input> -->
            </el-form-item>
            <el-form-item label="出版社">
              <el-input v-model="book.book.bookFactory" :model-value="book.book.bookFactory"></el-input>
            </el-form-item>
            <el-form-item label="发售日期">
              <el-date-picker v-model="book.book.bookAddDate" type="date" placeholder="选择日期 " format="YYYY/MM/DD"
                value-format="YYYY-MM-DD" />
            </el-form-item>
            <el-form-item label="数量">
              <el-input v-model="book.book.bookNum" :model-value="book.book.bookNum"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- <el-row> -->
        <!-- <el-form-item label="简介" prop="bookInfo" style="width:90%;padding-left:4%">
            <el-input type="textarea" v-model="book.book.bookInfo" prop="book.book.bookInfo" clearable
              :autosize="{ minRows: 2, maxRows: 4 }" maxlength="1000" placeholder="Please input"
              show-word-limit></el-input>
          </el-form-item> -->
        <!-- </el-row> -->

        <!-- <el-row>
          <el-form-item label="详情" style="width:90%;padding-left:4%">
          </el-form-item>
        </el-row> -->

        <el-collapse accordion>
          <el-collapse-item title="简介" name="1" style="width:90%;padding-left:4%">
            <el-input type="textarea" v-model="book.book.bookInfo" prop="book.book.bookInfo" clearable
              :autosize="{ minRows: 2, maxRows: 4 }" maxlength="100" placeholder="Please input"
              show-word-limit></el-input>
          </el-collapse-item>
          <el-collapse-item title="详情" name="2" style="width:90%;padding-left:4%">
            <el-input type="textarea" v-model="book.book.bookDetail" prop="book.book.bookInfo"
              :autosize="{ minRows: 2, maxRows: 4 }" clearable maxlength="1000" placeholder="Please input"
              show-word-limit></el-input>
          </el-collapse-item>

        </el-collapse>

        <el-row justify="space-evenly">
          <el-col span="24">
            <el-button type="primary" @click="alter()">修改</el-button>
          </el-col>
          <el-col span="24">
            <el-button type="success" @click="back()">返回</el-button>
          </el-col>
        </el-row>

      </div>

      <div style="width: 40%;float: right;margin-right: 1%">
        <el-image :src='book.book.bookPicture' style="width: 100%; height: 50%" v-show="havePicture"></el-image>
        <el-button type="warning" v-show="havePicture"><a href="api/test/download">下载图片</a></el-button>
        <el-button @click="havePicture = !havePicture" type="primary" v-show="havePicture">清空图片</el-button>
        <el-upload class=" upload-demo" drag action="api/test/up" multiple limit="100" encytype="multipart/form-data"
          ref="pict" name="photo" v-show="!havePicture" @keydown.y="handleBookPicture()">
          <el-icon class="el-icon--upload"><upload-filled /></el-icon>
          <div class="el-upload__text">
            <em>图片拖入</em> 或<em>点击上传</em>
            <p> 确定后点击 Y 键可查看上传的图片</p>
            重新上传图片请先清空列表
          </div>
          <template #tip>
            <div class="el-upload__tip">
              只能上传图片
            </div>
          </template>
        </el-upload>
      </div>
    </el-form>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";
import { reactive, onBeforeMount, toRef, ref } from "vue";
import axios from "axios";
import { UploadFilled } from '@element-plus/icons-vue'
let router = useRouter()

const prop = defineProps(['bookId'])

let bookId = toRef(prop, 'bookId')
console.log(bookId.value + "!!!!!!!!!!!!!!!!!!!!!!")


let havePicture = ref(true);
let handleBookPicture = (() => {
  setTimeout(() => {
    axios.get('api/getPicture').then(Response => {
      book.book.bookPicture = Response.data
      if (book.book.bookPicture != null && book.book.bookPicture != "")
        console.log(book.book.bookPicture + "图片")
      havePicture.value = true
    }).catch(Error => {
      alert(Error.message)
    })
  }, 500);
})


const book = reactive({
  book: {
    bookName: "",
    bookAddDate: "",
    bookAuthor: "",
    bookFactory: "",
    bookId: "",
    bookNum: "",
    bookPicture: "",
    bookPrice: "",
    seriesName: "",
    bookInfo: "",
    bookDetail: ""
  }
})

let series = ref('')
onBeforeMount(async () => {
  await axios.get(`api/lookup?bookId=${bookId.value}`).then(Response => {
    book.book = Response.data
    console.log(book.book.bookName + "@@@@@@@@@@@@@@@@@@@@@@@@@@@")
  })
  await axios.get('api/series').then(Response => {
    series.value = Response.data
    console.log(series.value)
  }).catch(Error => {
    alert(Error.message)
  })
})






let alter = (() => {
  let alterBook = JSON.stringify(book.book)
  axios.put('api/book', alterBook, { headers: { 'Content-Type': 'application/json' } }).then(Response => {
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
</style>
