import {HttpClient, json} from 'aurelia-fetch-client';

export class app {
  data1;
  nameOfSearch = "";

  constructor() {
    let client = new HttpClient();
    client.fetch("http://194.135.95.77:8080/api/getFileList")
      .then(response => response.json())
      .then(data => {
        this.data1 = data
      });
  }

  main() {
    let client = new HttpClient();
    client.fetch("http://194.135.95.77:8080/api/getFileList")
      .then(response => response.json())
      .then(data => {
      this.data1 = data
      });
  }

  selectedCategory(categoryID) {
    let client = new HttpClient();
    client.fetch("http://194.135.95.77:8080/api/getFileList?categoryId=" + categoryID)
      .then(response => response.json())
      .then(data => {
        this.data1 = data;
      });
  }

  searchByName() {
    var ans = this.nameOfSearch;
    let client = new HttpClient();
    client.fetch("http://194.135.95.77:8080/api/getFileList?query=" + ans)
      .then(response => response.json())
      .then(data => {
        this.data1 = data;
      });
  }
  
  setUploadUrl() {
	  var title = document.getElementById("uploadFileTitle").value;
	  var categoryId = document.getElementById("uploadFileCategoryId").value;
	  document.getElementById("fileUploadForm").action = "http://194.135.95.77:8080/api/uploadFile?title=" + title + "&categoryId=" + categoryId;
	  alert("hehe");
	  return true;
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

  header(obj) {
    var header = obj.title;
    return header
  }
}
