DROP TABLE IF EXISTS `reportes_acceso_no_autorizado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE `reportes_acceso_no_autorizado` (
  `Id_Rep` bigint unsigned NOT NULL AUTO_INCREMENT,
  `Id_Alu_FK` varchar(8) NOT NULL COMMENT 'Matricula del alumno que reporta',
  `Fecha_Reporte` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Estatus_Rep` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '1 Pendiente, 2 Bloqueada, 3 Resuelta',
  `Detalle_Rep` varchar(255) DEFAULT NULL COMMENT 'Comentario breve del alumno o sistema',
  `Bloqueada_Por_Usu_FK` varchar(40) DEFAULT NULL COMMENT 'Usuario que bloquea cuenta',
  `Fecha_Bloqueo` datetime DEFAULT NULL,
  `Obs_Bloqueo` varchar(255) DEFAULT NULL,
  `Reinicio_Por_Usu_FK` varchar(40) DEFAULT NULL COMMENT 'Usuario que reinicia password',
  `Fecha_Reinicio` datetime DEFAULT NULL,
  `Obs_Reinicio` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id_Rep`),
  KEY `idx_rep_alumno` (`Id_Alu_FK`),
  KEY `idx_rep_estatus` (`Estatus_Rep`),
  KEY `idx_rep_fecha` (`Fecha_Reporte`),
  KEY `idx_rep_bloqueada_por` (`Bloqueada_Por_Usu_FK`),
  KEY `idx_rep_reinicio_por` (`Reinicio_Por_Usu_FK`),
  CONSTRAINT `fk_rep_alumno` FOREIGN KEY (`Id_Alu_FK`) REFERENCES `alumnos` (`Id_Alu`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_rep_bloqueada_por_usuario` FOREIGN KEY (`Bloqueada_Por_Usu_FK`) REFERENCES `usuarios` (`Nombre_Usu`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_rep_reinicio_por_usuario` FOREIGN KEY (`Reinicio_Por_Usu_FK`) REFERENCES `usuarios` (`Nombre_Usu`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*!40101 SET character_set_client = @saved_cs_client */;
