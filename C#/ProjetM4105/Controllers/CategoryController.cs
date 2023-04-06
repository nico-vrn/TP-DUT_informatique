using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using ProjetM4105.Data.Models;
using ProjetM4105.Data.Services;
using ProjetM4105.Models;

namespace ProjetM4105.Controllers
{
    public class CategoryController : Controller
    {
        private PlatContext db = new PlatContext();


        public ActionResult Base()
        {
            return View();
        }

    }
}