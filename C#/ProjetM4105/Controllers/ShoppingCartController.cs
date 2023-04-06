using ProjetM4105.Data.Models;
using ProjetM4105.Models;
using ProjetM4105.ViewModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace ProjetM4105.Controllers
{
    public class ShoppingCartController : Controller
    {
        PlatContext storeDB = new PlatContext();
        //
        // GET: /ShoppingCart/
        public ActionResult Index()
        {
            var cart = ShoppingCart.GetCart(this.HttpContext);

            // Set up our ViewModel
            var viewModel = new ShoppingCartViewModel
            {
                CartItems = cart.GetCartItems(),
                CartTotal = cart.GetTotal()
            };
            // Return the view
            return View(viewModel);
        }
        PlatContext db = new PlatContext();

        public ActionResult AllOrders()
        {
            return View(db.Orders.ToList());
        }
        //Get: ShoppingCart/CreateOrder
        public ActionResult CreateOrder()
        {
            // Retrieve the order details from the database
            var cart = ShoppingCart.GetCart(this.HttpContext);

            // Save Order
            var order = new Order();
            order.Username = User.Identity.Name;
            order.FirstName = "Hervé";
            order.LastName = "Blanchon";
            order.Phone = "0783325243";
            order.Address = "1007 Mountain Drive, Gotham City";
            order.Email = order.FirstName + order.LastName + "@Gotham.com";
            order.OrderDetails = new List<OrderDetail>();

            // Iterate over the items in the cart, adding the order details for each
            foreach (var item in cart.GetCartItems())
            {
                var orderDetail = new OrderDetail
                {
                    PlatID = item.Plats.PlatID,
                    OrderID = order.OrderID,
                    UnitPrice = (decimal)item.Plats.UnitPrice,
                    Quantity = item.Count
                };
                order.OrderDetails.Add(orderDetail);
            }
            db.Orders.Add(order);
            // Save changes
            db.SaveChanges();
            // Clear the cart
            cart.EmptyCart();
            // Return to the main store page for more shopping
            return RedirectToAction("AllOrders");
        }

        public ActionResult NameAdress(){
            return View();
        }

        // POST: Plats1/Create
        [HttpPost, ActionName("Create")]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "OrderID,Username,FirstName,LastName,Address,Phone,Email,Total")] Order order)
        {
            var cart = ShoppingCart.GetCart(this.HttpContext);

            foreach (var item in cart.GetCartItems())
            {
                var orderDetail = new OrderDetail
                {
                    PlatID = item.Plats.PlatID,
                    OrderID = order.OrderID,
                    UnitPrice = (decimal)item.Plats.UnitPrice,
                    Quantity = item.Count
                };
                order.OrderDetails.Add(orderDetail);
            }
            db.Orders.Add(order);
            // Save changes
            db.SaveChanges();
            // Clear the cart
            cart.EmptyCart();
            // Return to the main store page for more shopping
            return RedirectToAction("AllOrders", "ShoppingCart");
        }

        //Get: Store/EmptyallCart
        public ActionResult EmptyAllCart()
        {
            var cart = ShoppingCart.GetCart(this.HttpContext);
            cart.EmptyCart();
            return RedirectToAction("Index","ShoppingCart");
        }


        //
        // GET: /Store/RemoveFromCart/5
        public ActionResult AddToCart(int id)
        {
            var addPlats = storeDB.Plats
                .Single(Plats => Plats.PlatID == id);

            // Add it to the shopping cart
            var cart = ShoppingCart.GetCart(this.HttpContext);

            cart.AddToCart(addPlats);

            // Go back to the main store page for more shopping
            return RedirectToAction("Index");
        }

        // GET: /Store/RemoveFromCart/5
        public ActionResult RemoveFromCart(int id)
        {
            // Retrieve plats from the database
            var addPlats = storeDB.Plats
                .Single(Plats => Plats.PlatID == id);

            // Add it to the shopping cart
            var cart = ShoppingCart.GetCart(this.HttpContext);

            cart.RemoveFromCart(addPlats);

            // Go back to the main store page for more shopping
            return RedirectToAction("Index");
        }

        //
        // GET: /ShoppingCart/CartSummary
        [ChildActionOnly]
        public ActionResult CartSummary()
        {
            var cart = ShoppingCart.GetCart(this.HttpContext);

            ViewData["CartCount"] = cart.GetCount();
            return PartialView("CartSummary");
        }
    }
}