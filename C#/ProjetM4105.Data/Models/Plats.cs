using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;


namespace ProjetM4105.Data.Models
{
    public class Plats
    {
        [Key]
        public int PlatID { get; set; }
        [Required]
        public string PlatName { get; set; }
        [Required]
        public string Description { get; set; }
        [Required]
        public string ImagePath { get; set; }
        [Required]
        public double? UnitPrice { get; set; }
        [Required]
        public Category Category { get; set; }

    }
}
