import {Injectable} from '@angular/core';
import {Item} from "./item";
import {Observable, of} from "rxjs";
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  constructor(private http: HttpClient) {
  }

  private itemsUrl = 'https://eurder-switchfully-solution.herokuapp.com/items';
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  getItems(): Observable<Item []> {
    return this.http.get<Item[]>(this.itemsUrl)
  }

  getItem(id: string): Observable<Item> {
    const url = `${this.itemsUrl}/${id}`;
    return this.http.get<Item>(url);
  }

  searchItems(term: string): Observable<Item[]> {
    if (!term.trim()) {
      return of([]);
    }
    return this.http.get<Item[]>(`${this.itemsUrl}/?name=${term}`);
  }

  createItem(name, description, price, amount): Observable<Item> {
    const item = {
      id: 10,
      name: name,
      description: description,
      price: price,
      stockUrgency: "to do",
      amountOfStock: amount
    };
    return this.http.post <Item>(`${this.itemsUrl}/create`, item);
  }

  updateItem(item: Item): Observable<any> {
    return this.http.put(this.itemsUrl, item, this.httpOptions);
  }
}
