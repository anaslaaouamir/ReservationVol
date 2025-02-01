export interface Client {
  idClient?: number;
  nom: string;
  email: string;
  motPasse: string;
  telephone: string;
}

export interface AuthResponse {
  token: string;
}
