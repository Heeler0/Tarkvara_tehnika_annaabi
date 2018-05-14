import {HttpClient, json} from 'aurelia-fetch-client';

export class app {
  data1;
  nameOfSearch = "";
  category;
  createUserName;
  createPassword;
  createEmail;

  sessionID;
  userID;
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
    if (document.cookie !== "") {
      var cookie = JSON.parse(document.cookie);
      this.sessionID = cookie.session;
      this.userID = cookie.user;
    }
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
	var postUrl = "http://194.135.95.77:8080/api/postComment?fileId=" + fileId + "&comment=" + comment + "&token=" + this.sessionID;
    if (comment.length > 1) {
      document.getElementById("submit" + fileId).removeAttribute("disabled");
      let client = new HttpClient();
      client.fetch(postUrl);

      this.comments(fileId);
      this.comments(fileId);
      document.getElementById("commentArea" + fileId).value = "";
    } else {
      alert("Kommentaar liiga lühike (min 2)")
    }
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
    document.getElementById("fileUploadForm").action = "http://194.135.95.77:8080/api/uploadFile?title=" + title + "&categoryId=" + categoryId + "&token=" + this.sessionID;
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
    let client = new HttpClient();
    client.fetch("http://194.135.95.77:8080/api/login", {
      method: "Post",
      body: formData
    })
      .then(response => response.json())
      .then(body => {
      var session = body.token;
      var id = body.userId;
		  if (session.length !== 16) {
			  alert(body);
		  }
		  else {
			  this.sessionID = session;
			  this.userID = id;
			  var text = '{"session":"'+ session + '", "user":"'+ id +'"}';
			  document.cookie = text;
		  }
		  this.userName = "";
		  this.password = "";
		  this.updateData();
		  this.updateData();
	});
  }

  logout() {
	  this.sessionID = "";
	  this.userID = "";
	  document.cookie = "";
    this.updateData();
    this.updateData();
  }

  castVote(id, vote) {
    let client = new HttpClient();
    client.fetch("http://194.135.95.77:8080/api/vote?fileId=" + id + "&score=" + vote + "&token=" + this.sessionID);
    this.updateData();
    this.updateData();
  }

  updateData() {
    let client = new HttpClient();
    client.fetch(this.latestUrl)
      .then(response => response.json())
      .then(data => {
        this.data1 = data;
      });
  }

  deleteFileFromDatabase(fileID) {
    let client = new HttpClient();
    client.fetch("http://194.135.95.77:8080/api/deleteFile?fileId="+ fileID +"&token=" + this.sessionID);
    this.updateData();
    this.updateData();
  }

  uploaderCheck(obj) {
    if (this.userID == obj.uploaderId) {
      return true;
    } else {
      return false;
    }
  }

  searchByCategory() {
    var ans = this.nameOfSearch;
    var category = this.category;
    let client = new HttpClient();
    client.fetch("http://194.135.95.77:8080/api/getFileList?query=" + ans + "&categoryId" + category)
      .then(response => response.json())
      .then(data => {
        this.data1 = data;
      });
    this.latestUrl = "http://194.135.95.77:8080/api/getFileList?query=" + ans + "&categoryId" + category;
  }

}
