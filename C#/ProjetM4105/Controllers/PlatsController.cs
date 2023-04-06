using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using ProjetM4105.Data.Services;
using System.Web.ModelBinding;
using ProjetM4105.Data.Models;
using System.Net;
using ProjetM4105.Models;
using System.Data.Entity;

namespace ProjetM4105.Controllers
{
    public class PlatsController : Controller
    {

        // GET: Plats
        public ActionResult AllPlats()
        {
            return View(db.Plats.ToList());
        }

        
        private readonly IDishData _dataProvider;


        // Simple Mapper
        private Dictionary<int, Category> _simpleMapper = new Dictionary<int, Category>() {
            { 0, Category.Entree },
            { 1, Category.Plat },
            { 2, Category.Boisson},
            { 3, Category.Dessert },
        };
        
        public PlatsController(IDishData dataProvider)
        {
            _dataProvider = dataProvider;
        }


        public ActionResult ListPlats(int id)
        {
            var category = new DishCategory(_simpleMapper[id],
                                            _simpleMapper[id].ToString(),
                                            _dataProvider.GetDishesByCategory(_simpleMapper[id]));

            return View(category);
        }

        private PlatContext db = new PlatContext();
        public ActionResult PlatsDetails(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Plats plats = db.Plats.Find(id);
            if (plats == null)
            {
                return HttpNotFound();
            }
            return View(plats);
        }
        // GET: Plats1/Edit/5
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Plats plats = db.Plats.Find(id);
            if (plats == null)
            {
                return HttpNotFound();
            }
            return View(plats);
        }

        // POST: Plats1/Edit/5
        // Afin de déjouer les attaques par survalidation, activez les propriétés spécifiques auxquelles vous voulez établir une liaison. Pour 
        // plus de détails, consultez https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "PlatID,PlatName,Description,ImagePath,UnitPrice,CategoryID")] Plats plats)
        {
            if (ModelState.IsValid)
            {
                db.Entry(plats).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("AllPlats");
            }
            return View(plats);
        }

        // GET: Plats1/Delete/5
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Plats plats = db.Plats.Find(id);
            if (plats == null)
            {
                return HttpNotFound();
            }
            return View(plats);
        }

        // POST: Plats1/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            Plats plats = db.Plats.Find(id);
            db.Plats.Remove(plats);
            db.SaveChanges();
            return RedirectToAction("AllPlats");
        }
        public ActionResult Create()
        {
            return View();
        }

        // POST: Plats1/Create
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "PlatID,PlatName,Description,ImagePath,UnitPrice,CategoryID")] Plats plats)
        {
            if (ModelState.IsValid)
            {
                db.Plats.Add(plats);
                db.SaveChanges();
                return RedirectToAction("AllPlats");
            }

            return View(plats);
        }
    }
}