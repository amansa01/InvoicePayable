import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {CreateVendorService} from "../vendor.service";
import {UserDetailsService} from "../../userdetail.service";

@Component({
  selector: 'vendor-invoice',
  templateUrl: './vendorsummary.component.html'
  // styleUrls: ['./vendorsummary.component.css']

})
export class VendorsummaryComponent implements OnInit{

  items: any;
  email:string;
 test:any;
  constructor(private router: Router,private createvendorservise: CreateVendorService, private userDetailsService:UserDetailsService){}

  ngOnInit() {
    console.log('In NgonIt');

    this.email= this.userDetailsService.user.auth.providerData[0].email;
   this.test = {'email': this.email};
    this.createvendorservise.vendorSummary(this.test).then((Response) => {
      this.items = Response.json();
      console.log('printing items',this.items);
     }).catch((error) => {
  console.log(error);
});
  }

}
