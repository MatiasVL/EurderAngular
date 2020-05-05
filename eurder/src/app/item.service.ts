import {Injectable} from '@angular/core';
import {Item} from "./item";
import {Observable, of} from "rxjs";
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  constructor(private http: HttpClient) {
  }
  private itemsUrl = 'api/items';

  getItems(): Observable<Item []> {
  return this.http.get<Item[]>(this.itemsUrl)
  }

  getItem(id: string): Observable<Item> {
    const url = `${this.itemsUrl}/${id}`;
    return this.http.get<Item>(url);
  }

  searchItems(term: string): Observable<Item[]>{
    if (!term.trim()){
      return of([]);
    }
    return this.http.get<Item[]>(`${this.itemsUrl}/?name=${term}`);
  }
}
