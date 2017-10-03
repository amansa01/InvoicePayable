export class ConstantComponent {

  checkVendorList: string;
  createVendorList: string;
  uploadBills: string;
  uploadBill:string;
  vendorInvoice:string;
  vendorSummary:string;
  vendorList:string;
  payment:string;
  session:string;
  login:string;
  clearSession:string;
  totalInvoice:string;
  totalVendor:string
  pagelist:string;
  search:any;
  sort:any;
  sortList:any;
  verifyToken;
  vendorSearch;

  searchTotal:string;
  constructor() { this.checkVendorList = 'http://localhost:8080/invoicepayable-0.0.1-SNAPSHOT/vendor/checkvendorlist';
    this.createVendorList = 'http://localhost:8080/invoicepayable-0.0.1-SNAPSHOT/vendor/createVendorList';
    this.uploadBills = 'http://localhost:8080/invoicepayable-0.0.1-SNAPSHOT/invoice/checkInvoice';
    this.uploadBill = 'http://localhost:8080/invoicepayable-0.0.1-SNAPSHOT/invoice/createInvoice';
    this.vendorInvoice='http://localhost:8080/invoicepayable-0.0.1-SNAPSHOT/invoice/invoiceDetail';
    this.vendorSummary='http://localhost:8080/invoicepayable-0.0.1-SNAPSHOT/invoice/invoiceSummary';
    this.vendorList='http://localhost:8080/invoicepayable-0.0.1-SNAPSHOT/vendor/vendorlist/';
    this.payment='http://localhost:8080/invoicepayable-0.0.1-SNAPSHOT/invoice/payment';
    this.session='http://localhost:8080/invoicepayable-0.0.1-SNAPSHOT/vendor/checkSession';
    this.login='http://localhost:8080/invoicepayable-0.0.1-SNAPSHOT/vendor/login';
    this.clearSession='http://localhost:8080/invoicepayable-0.0.1-SNAPSHOT/vendor/clearSession';
    this.totalInvoice='http://localhost:8080/invoicepayable-0.0.1-SNAPSHOT/invoice/total';
    this.pagelist='http://localhost:8080/invoicepayable-0.0.1-SNAPSHOT/invoice/pageList/';
    this.search='http://localhost:8080/invoicepayable-0.0.1-SNAPSHOT/invoice/search/';
    this.sort='http://localhost:8080/invoicepayable-0.0.1-SNAPSHOT/invoice/sort';
    this.sortList='http://localhost:8080/invoicepayable-0.0.1-SNAPSHOT/invoice/sortList/';
    this.searchTotal='http://localhost:8080/invoicepayable-0.0.1-SNAPSHOT/invoice/searchTotal';
    this.verifyToken='http://localhost:8080/invoicepayable-0.0.1-SNAPSHOT/vendor/verify';
    this.vendorSearch='http://localhost:8080/invoicepayable-0.0.1-SNAPSHOT/vendor/search';
    this.totalVendor='http://localhost:8080/invoicepayable-0.0.1-SNAPSHOT/vendor/total';
  }

}
