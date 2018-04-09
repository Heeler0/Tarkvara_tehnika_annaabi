import {HttpClient, json} from 'aurelia-fetch-client';

export class app {
  data1;
  nameOfSearch = "";
  createUserName;
  createPassword;
  createEmail;

  sessionID;
  userName;
  password;

  latestUrl;

  constructor() {
    let client = new HttpClient();
    client.fetch("http://194.135.95.77:8080/api/getFileList")
      .then(response => response.json())
      .then(data => {
        this.data1 = data
      });
    this.latestUrl = "http://194.135.95.77:8080/api/getFileList";
  }

  main() {
    let client = new HttpClient();
    client.fetch("http://194.135.95.77:8080/api/getFileList")
      .then(response => response.json())
      .then(data => {
      this.data1 = data
      });
    this.latestUrl = "http://194.135.95.77:8080/api/getFileList";
  }

  selectedCategory(categoryID) {
    let client = new HttpClient();
    client.fetch("http://194.135.95.77:8080/api/getFileList?categoryId=" + categoryID)
      .then(response => response.json())
      .then(data => {
        this.data1 = data;
      });
    this.latestUrl = "http://194.135.95.77:8080/api/getFileList?categoryId=" + categoryID;
  }

  searchByName() {
    var ans = this.nameOfSearch;
    let client = new HttpClient();
    client.fetch("http://194.135.95.77:8080/api/getFileList?query=" + ans)
      .then(response => response.json())
      .then(data => {
        this.data1 = data;
      });
    this.latestUrl = "http://194.135.95.77:8080/api/getFileList?query=" + ans;
  }

  convertToString(text) {
    return JSON.stringify(text);
  }

  convertDate(number) {
    var date = new Date(number * 1000);
    var day = date.getDate();
    var monthIndex = date.getMonth() + 1;
    var year = date.getFullYear();
    return day + '.' + monthIndex + '.' + year;
  }

  getDate(obj) {
    var number = obj.uploadDate;
    return number;
  }

  getFileName(obj) {
    var header = obj.fileName;
    return header
  }

  getDesc(obj) {
    var desc = obj.fileDescription;
    var newchar = '<br>';
    desc = desc.split('\n').join(newchar);
    return desc;
  }

  getSize(obj) {
    var size = obj.fileSize;
    size = size / 1024;
    size = Math.round(size);
    return size + "KB";
  }

  download(obj) {
    var url = "http://194.135.95.77:8080/api/getFile/";
    url += obj.fileName;
    return url;
  }
  
  postComment(fileId) {
	var comment = document.getElementById("commentArea"+fileId).value;
	var postUrl = "http://194.135.95.77:8080/api/postComment?fileId=" + fileId + "&comment=" + comment;
	
	let client = new HttpClient();
	client.fetch(postUrl);
	
	this.comments(fileId);
	this.comments(fileId);
  }

  comments(fileId) {
    let client = new HttpClient();
    client.fetch("http://194.135.95.77:8080/api/getComments?fileId=" + fileId)
      .then(response => response.json())
      .then(data => {
		var commentsHtml = "";
		
		for (var i = 0; i < data.length; i++){
			var commentObject = data[i];
			var commentContent = commentObject["comment"];
			
			var commentHtml = commentContent + "<br><hr>"; // comment html
			commentsHtml += commentHtml;
		}
		
		document.getElementById("comments"+fileId).innerHTML = commentsHtml;
      });
	  
	  return true;
  }
  
  getComments(obj) {
    var com = obj.id;
    console.log(com);
    return com;
  }

  getFileID(obj) {
    var id = obj.id;
    return id;
  }

  setUploadUrl() {
    var title = document.getElementById("uploadFileTitle").value;
    var categoryId = document.getElementById("uploadFileCategoryId").value;
    document.getElementById("fileUploadForm").action = "http://194.135.95.77:8080/api/uploadFile?title=" + title + "&categoryId=" + categoryId;
    return true;
  }

  getHeader(obj) {
    return obj.title;
  }

  createUser() {
	var formData = new FormData();
	formData.append('name', this.createUserName);
	formData.append('password', this.createPassword);
	formData.append('email', this.createEmail);
	
    let httpClient = new HttpClient();
    httpClient.fetch("http://194.135.95.77:8080/api/registerAccount", {
      method: "POST",
      body:  formData
    });
    this.createUserName = "";
    this.createPassword = "";
    this.createEmail = "";
  }

  getVote(obj) {
    return obj.voteCount;
  }

  getUploaderUserName(obj) {
    return obj.uploaderName;
  }

  login() {
    var formData = new FormData();
    formData.append('name', this.userName);
    formData.append('password', this.password);

  }

  logout() {

  }

  castVote(id, vote) {
    let client = new HttpClient();
    client.fetch("http://194.135.95.77:8080/api/vote?fileId=" + id + "&score=" + vote)
    this.updateData()
  }

  updateData() {
    let client = new HttpClient();
    client.fetch(this.latestUrl)
      .then(response => response.json())
      .then(data => {
        this.data1 = data;
      });
  }

}
