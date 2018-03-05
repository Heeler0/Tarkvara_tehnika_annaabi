import {HttpClient, json} from 'aurelia-fetch-client';

export class app {
  data1;

  getJson () {
    let client = new HttpClient();
    client.fetch("http://194.135.95.77:8080/api/getFileList")
      .then(response => response.json())
      .then(data => {
        this.data1 = data
      });
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
    size = Math.round(size)
    return size + "KB";
  }

  download(obj) {
    var url = "http://194.135.95.77:8080/api/getFile/";
    url += obj.fileName;
    return url;
  }

  doUpload() {
    return this.upload('http://194.135.95.77:8080/api/uploadFile', {}, this.selectedFiles[0]).then(() => this.clearFiles());
  }

  upload(url, data, files, method = "POST") {
    let formData = new FormData();

    for (let i = 0; i < files.length; i++) {
      formData.append(`files[${i}]`, files[i]);
    }

    return this.http.fetch(url, {
      method: method,
      body: formData,
      headers: new Headers()
    }).then(response => response.json());
  }
  constructor () {

    this.http = new HttpClient();
    this.http.configure(config => { config
      .useStandardConfiguration()
      .withBaseUrl(`http://194.135.95.77:8080/api/uploadFile`); // hetkene väljund oleks http://localhost:9000/api/uploadapi/upload
    });
  }
}
