
CREATE DATABASE IF NOT EXISTS temperaturas_2019 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci;

CREATE TABLE IF NOT EXISTS temperaturas_2019.mediciones (
  
  nombre_provincia varchar(30) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  mes enum('ENERO','FEBRERO','MARZO','ABRIL','MAYO','JUNIO','JULIO','AGOSTO','SEPTIEMBRE','OCTUBRE','NOVIEMBRE','DICIEMBRE') NOT NULL,
  tem_med_min double NOT NULL,
  tem_med_med double NOT NULL,
  tem_med_max double NOT NULL,
  prec_media double NOT NULL,
  PRIMARY KEY (nombre_provincia,mes)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
