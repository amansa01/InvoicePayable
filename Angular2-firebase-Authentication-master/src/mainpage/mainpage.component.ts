
import {Component} from "@angular/core";

@Component({
  selector: 'main-page',
  templateUrl: './mainpage.html',
  styleUrls: ['./mainpage.component.css']
})
export class MainpageComponent{
  email;
  constructor(){
    this.email = window.localStorage.getItem('vendorEmail');
  }
}
