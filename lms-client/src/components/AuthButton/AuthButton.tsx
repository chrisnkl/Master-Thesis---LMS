import {Link} from "react-router-dom";

export default function AuthButton() {
  return (
      <div className={"w-50 h-40 flex flex-col shadow-md p-4"}>
        <span></span>
        <form>

          <label htmlFor={"username"}></label>
          <input type={"text"}/>

          <button className="btn-primary">Login</button>
          
        </form>
        <Link to={""} className={""}>Login with microsoft</Link>
      </div>
  )
}