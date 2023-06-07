<template>
	<div>
		<p v-show="false">{{ book.book.bookName }}</p>
		<el-row>
			<el-col :span="12">
				<h2>书籍简介</h2>
				<el-card :body-style="{ padding: '50px 50px'}" shadow="hover" style="width: 80%;align-content: center">
					<div class="info_div"><span class="info_span">书名:</span><span>{{ book.book.bookName }}</span></div>
					<div class="info_div"><span class="info_span">价格:</span><span
							style="color: orangered">{{ book.book.bookPrice }}</span></div>
					<div class="info_div"><span class="info_span">作者:</span><span>{{ book.book.bookAuthor }}</span>
					</div>
					<div class="info_div"><span class="info_span">出版社:</span><span>{{ book.book.bookFactory }}</span>
					</div>
					<div class="info_div"><span class="info_span">出版日期:</span><span
							style="color: deepskyblue">{{ book.book.bookAddDate }}</span></div>
					<h4 class="h3" style="float: left;width: 100%"><span
							style="margin-right: 20%">购买数量:</span>{{number}}</h4>
					<div style="text-align: center;margin-top: 2%">
						<el-button @click="number++" type="info" round>加一</el-button>
						<el-button @click="number--" type="info" round v-if="number>1">减一</el-button>
						<el-button style="float: left;width: 50%" type="warning" @click="buy()">购买</el-button>
					</div>
				</el-card>
			</el-col>

			<el-col :span="12">
				<el-card shadow="hover" style="margin-top:10%;width: 80%;align-content: center;text-align: center">
					<el-image :src="require('@/assets/static/'+book.book.bookPicture +'.jpg')"></el-image>
					<h6>图片仅供参考,具体以实物为准!</h6>
				</el-card>
			</el-col>
		</el-row>
	</div>
</template>

<script setup>
	import axios from "axios";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
let router=useRouter()
let store=useStore()
	import {
		reactive,
		ref
	} from "vue";
	let number = ref(1)
	const props = defineProps(['id'])

	// let book = ref(null)

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


	axios.get(`api/lookup?bookId=${props.id}`).then(res => {
		book.book = res.data
	})
	console.log(book)

	let bag = reactive({
		bookId: '',
		userId: '',
		bookNumber: ''
	})

	let buy = (() => {
		bag.bookId = book.book.bookId;
		console.log("----" + bag.bookId)
		bag.userId = store.state.userId;
		console.log(bag.userId)
		if (store.state.userId == "" || store.state.userId == undefined || store.state.userId == null) {
			router.push({
				name: 'login'
			})
		} else {
			bag.bookNumber = number.value;
			console.log(bag.bookNumber)
			let addBag = JSON.stringify(bag)

			axios.post(`api/bag`, addBag, {
				headers: {
					'Content-Type': 'application/json'
				}
			}).then(Response => {
				let message = Response.data;
				if (confirm("是否前往购物车?")) {
					router.push({
						name: 'userBag'
					})
				}
			}).catch(Error => {
				console.log(Error)
			})
		}
	})
</script>

<style scoped>
	.info_div {
		margin-bottom: 8%
	}

	.info_span {
		width: 30%;
		margin-right: 15%;
	}
</style>