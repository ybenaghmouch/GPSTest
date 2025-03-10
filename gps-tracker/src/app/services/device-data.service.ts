import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DeviceDataService {
  private baseUrl = '/api/device-data'; // Base URL for the API

  constructor(private http: HttpClient) {}

  // Fetch movement data by device ID
  getMovementByIdDevice(id_device: number): Observable<{ latitude: number; longitude: number }[]> {
    return this.http.get<{ latitude: number; longitude: number }[]>(`${this.baseUrl}/movement`, {
      params: { id_device: id_device.toString() }
    });
  }

  // Fetch device data by device ID and date
  getDeviceDataByIdAndDate(id_device: number, date: string): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/info`, {
      params: { id_device: id_device.toString(), Date: date }
    });
  }
}