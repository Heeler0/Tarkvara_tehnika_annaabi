import {HttpClient, json} from 'aurelia-fetch-client';

export class app {
  data;

  getJson () {
    //console.log("Hey");
    //let client = new HttpClient();
    //client.fetch('http://194.135.95.77:8080/api/getFileList', {
      //'method' : "GET",
      //headers:{'Access-Control-Allow-Origin':'*'
      //}
    //})
      //.then(console.log("Hey"));
    var test = '[ { "id" : 1, "fileName" : "matemaatika.pdf", "fileDescription" : "Matemaatika õppematerjal.", "fileSize" : 124512, "uploadDate" : 1251235612 }, { "id" : 3, "fileName" : "infotehnoloogia.docx", "fileDescription" : "Infotehnoloogia õppematerjal.", "fileSize" : 3541782, "uploadDate" : 135412445 }, { "id" : 2, "fileName" : "füüsika.pdf", "fileDescription" : "Füüsika õppematerjal.", "fileSize" : 523121, "uploadDate" : 189053123 }, { "id" : 4, "fileName" : "file", "fileDescription" : "File: file", "fileSize" : 49961, "uploadDate" : null }, { "id" : 5, "fileName" : "Odin3 v3.11.1.zip", "fileDescription" : "File: Odin3 v3.11.1.zip", "fileSize" : 976363, "uploadDate" : null }, { "id" : 6, "fileName" : "cstrike.zip", "fileDescription" : "File: cstrike.zip", "fileSize" : 790493, "uploadDate" : 1520185200 }, { "id" : 7, "fileName" : "Probleemid.docx", "fileDescription" : "File: Probleemid.docx", "fileSize" : 10895, "uploadDate" : 1520186598 }, { "id" : 8, "fileName" : "Introduction into Non.docx", "fileDescription" : "File: Introduction into Non.docx", "fileSize" : 345592, "uploadDate" : 1520186627 }, { "id" : 9, "fileName" : "Task 07.docx", "fileDescription" : "File: Task 07.docx", "fileSize" : 200437, "uploadDate" : 1520186660 }, { "id" : 10, "fileName" : "audit.docx", "fileDescription" : "File: audit.docx", "fileSize" : 13113, "uploadDate" : 1520186825 } ]'
    var testing = JSON.parse(test)
    this.data = testing
  }

  convertToString(text) {
    return JSON.stringify(text);
  }

  convertDate(number) {
    var date = new Date(number * 1000);
    return date;
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
    return desc;
  }

  doUpload() {
    //console.log("Works");
    return this.upload('api/upload', {}, this.selectedFiles[0]).then(() => this.clearFiles());
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
      .withBaseUrl(`api/upload`); // hetkene väljund oleks http://localhost:9000/api/uploadapi/upload
    });
  }
}
