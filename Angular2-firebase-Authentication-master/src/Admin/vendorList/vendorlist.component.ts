import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {CreateVendorService} from "../../vendor/vendor.service";
import {UserDetailsService} from "../../userdetail.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'create-bill',
  templateUrl: './vendorlist.component.html',
 styleUrls:['./vendorlist.component.css']
})
export class VendorlistComponent implements OnInit {

  items: any;
  searchForm:any
  email: string;
  itemsss:null;
  paginationArrayForInvoice;
  totalpages;
  end;
  start;
  searchresult;
  pageList: any;
  test: any;
  search = {
    "text": '',
  }
  private searchVendorForm: FormGroup;
  emaill:any;

  constructor(private _fb: FormBuilder,private router: Router, private createvendorservise: CreateVendorService, private userDetailsService: UserDetailsService) {

  }

  ngOnInit() {
    console.log('In NgonIt');

    this.email = this.userDetailsService.user.auth.providerData[0].email;
    // this.test = {'email': this.email};
    this.createvendorservise.vendorList(this.email,0,3).then((Response) => {
      this.pageList = Response.json();
      console.log('printing items', this.pageList);
    }).catch((error) => {
      console.log(error);
    });
    this.searchVendorForm = this._fb.group({
      text: [null, Validators.required],
    });
this.invoicePagination();


    //
    // console.log('Before Page List method');
    // this.createvendorservise.pageList(this.email, 0, 3).then((response) => {
    //   this.pageList = response.json();
    //   console.log('loadInvoiceList', response);
    // }).catch((error) => {
    //   console.log(error);
    // });


  }
  vendorDetails(emaill:any)
  {
    this.test = {'email': emaill};
    this.createvendorservise.vendorSummary(this.test).then((Response) => {
      console.log('Getting response ', Response.json());

        this.itemsss = Response.json();
        console.log('printing items', this.items);
    }).catch((error) => {
      console.log(error);
    });
  }


  onVendorSearch() {
    this.search.text = this.searchVendorForm.value.text;
      this.createvendorservise.onVendorSearch(this.search.text).then((Response) => {
      this.searchresult = Response.json();
      console.log('Received Search Results' + Response.json());
    }).catch((error) => {
      console.log(error);
    });

  }







  invoicePagination() {
    this.createvendorservise.getCountofTotalVendors().then((Response) => {
      console.log('In Vendor Pagination Method', Response.json());
      this.totalpages = Response.json();
      this.PaginationArrayCallBackFunction();
    }).catch((error) => {
      console.log(error);
    });
  }


  PaginationArrayCallBackFunction() {

    this.paginationArrayForInvoice = this.createvendorservise.getPaginationArray(this.totalpages);
    console.log('Total No Of Pages We Got ', this.paginationArrayForInvoice)
  }


  loadVendorList(pageid) {
    this.email = this.userDetailsService.user.auth.providerData[0].email;
    this.start = ( pageid - 1) * 3;
    this.end = pageid * 3;
    this.createvendorservise.vendorList(this.email, this.start, this.end).then((response) => {
      this.pageList = response.json();
      console.log('Loading VEndor List', response);
    }).catch((error) => {
      console.log(error);
    });
  }






}
