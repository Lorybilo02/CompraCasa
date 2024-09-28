import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SharedService {
  private state: any = {};
  public selectValueSource : string = '';
  public percent : string = '0%';
  public firstFormData: any = {};
  public secondFormData: any = {};
  public typeFormData: any = {};
  public priceBarValue: string = '';
  public id: number = 0;
  public description = '';
  public starValue : string = '';
  public price: number = 0;
  public sqm : number = 0;
  constructor() {}

  setSelectValue(value: string) {
    this.selectValueSource = (value);
  }
  setPriceBar(value: string){
      this.priceBarValue=value;
  }
  setPercent(value: string){
      this.percent = (value);
  }

  getCharFormData(){
      return this.firstFormData;
  }

  setCharFormData(data: any){
      this.firstFormData = data;
  }

  resetCharFormData(){
      this.firstFormData = {};
  }
    getDescriptionFormData(){
        return this.secondFormData;
    }

    setDescriptionFormData(data: any){
        this.secondFormData = data;
    }

    resetDescriptionFormData(){
        this.firstFormData = {};
    }

    getTypeFormData(){
        return this.typeFormData;
    }

    setTypeFormData(data: any){
        this.typeFormData = data;
    }

    resetTypeFormData(){
        this.typeFormData = {};
    }

    set(key: string, value: any): void {
        this.state[key] = value;
    }

    get(key: string): any {
        return this.state[key];
    }

    clear(): void {
      this.state = {};
      console.log("state cleared")
    }

    clearAll(): void {
        this.state = {};
    }

}
