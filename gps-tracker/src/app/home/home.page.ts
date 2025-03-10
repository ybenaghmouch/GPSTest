import { Component, OnInit } from '@angular/core';
import * as L from 'leaflet';
import { DeviceDataService } from '../services/device-data.service';
import { AlertController } from '@ionic/angular';

@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
  standalone:false
})

export class HomePage implements OnInit {
  
  private map!: L.Map;
  
  deviceId: number | null = null;

  constructor(private deviceDataService: DeviceDataService,
    private alertController: AlertController
  ) {}

  ngOnInit(): void {
    delete (L.Icon.Default.prototype as any)._getIconUrl;
    L.Icon.Default.mergeOptions({
      iconRetinaUrl: 'assets/marker-icon-2x.png',
      iconUrl: 'assets/marker-icon.png',
      shadowUrl: 'assets/marker-shadow.png',
    });
    // Initialize the map on load
    this.initMap();
  }

  private initMap(): void {
    // Center the map on London with zoom 13
    this.map = L.map('map').setView([51.505, -0.09], 13);
  
    // Add an OSM tile layer
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; OpenStreetMap contributors'
    }).addTo(this.map);
  
    // Add a marker
    const marker = L.marker([51.5, -0.09]).addTo(this.map);
    marker.bindPopup('I am a standalone popup.').openPopup();
  
    // Add a circle
    const circle = L.circle([51.508, -0.11], {
      color: 'red',
      fillColor: '#f03',
      fillOpacity: 0.5,
      radius: 500
    }).addTo(this.map);
    circle.bindPopup('I am a circle.');
  
    // Add a polygon
    const polygon = L.polygon([
      [51.509, -0.08],
      [51.503, -0.06],
      [51.51, -0.047]
    ]).addTo(this.map);
    polygon.bindPopup('I am a polygon.');
  }
  ionViewDidEnter() {
    setTimeout(() => {
      this.map.invalidateSize();
    }, 0);
  }

  onLoadDeviceData(): void {
    if (this.deviceId) {
      this.loadMovementData(this.deviceId);
    }
  }

  private loadMovementData(id_device: number): void {
    this.deviceDataService.getMovementByIdDevice(id_device).subscribe({
      next: (data) => {
        const coordinates: L.LatLngTuple[] = data.map((point: any) => [
          point.latitude,
          point.longitude
        ]);
        const polyline = L.polyline(coordinates, { color: 'blue' }).addTo(this.map);
        this.map.fitBounds(polyline.getBounds());
        this.animateMarker(coordinates);
      },
      error: (err) => {
        if (err.status >= 500) {
          // Server error
          this.presentAlert('Server Error!', 'Something went wrong on the server');
        } else if (err.status >= 400 && err.status < 500) {
          // ID not found
          this.presentAlert('Invalid ID', 'This device ID does not exist');
        } else {
          this.presentAlert('Error', 'An unexpected error occurred');
        }
      }
    });
  }
  private async presentAlert(header: string, message: string) {
    const alert = await this.alertController.create({
      header,
      message,
      buttons: ['OK']
    });
    await alert.present();
  }

  private animateMarker(coordinates: L.LatLngTuple[]): void {
    
    const marker = L.marker(coordinates[0]).addTo(this.map);
    let index = 0;

    const interval = setInterval(() => {
      if (index >= coordinates.length - 1) {
        clearInterval(interval);
        return;
      }
      index++;

      
      marker.setLatLng(coordinates[index]);

      // show a popup on each step
      marker
        .bindPopup(
          `Point ${index + 1}: Latitude ${coordinates[index][0]}, Longitude ${
            coordinates[index][1]
          }`
        )
        .openPopup();
    }, 1000);
  }
}