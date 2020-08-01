import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DomSanitizer } from '@angular/platform-browser';

export class Input {
  public id: number;
  public info: string;
  public url: string;
  public emergencyNum: number;
  public smsNum: number;
  public smsMsg: string;
}

@Component({
  selector: 'app-qrcode',
  templateUrl: './qrcode.component.html',
  styleUrls: ['./qrcode.component.css']
})
export class QrcodeComponent implements OnInit {

  model = new Input();
  public imageUrl:any = null;

  constructor(private http: HttpClient, private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
  }
  
  onSubmit(form) {
    const headers = { 'content-type': 'application/json', 'Accept': 'image/png'};
    console.log(form.value);
    let body = JSON.stringify(form.value);
    console.log(body);

    this.http.post('http://localhost:8080/api/genqrcode', 
                    body, 
                    {'headers':headers, 
                      'responseType':'blob'}).subscribe(
      (response) => {
        console.log(response);
        //let objectURL = 'data:image/png;base64,' + response;
        //this.imageUrl = this.sanitizer.bypassSecurityTrustUrl(objectURL);
        this.createImageFromBlob(response);
    },
      (error) => console.log(error)
    );
    
  }
  
  createImageFromBlob(image: Blob) {
   let reader = new FileReader();
   reader.addEventListener("load", () => {
      this.imageUrl = reader.result;
   }, false);

   if (image) {
      reader.readAsDataURL(image);
   }
}

}
