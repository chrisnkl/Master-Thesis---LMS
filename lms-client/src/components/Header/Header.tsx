import {Link} from "react-router-dom";

export default function Header() {
  return (
    <header className="flex justify-between">
      <Link to={"/"} className={"text-2xl font-bold"}>eLMS</Link>
      <nav className={"flex gap-4"}>
        <Link to={"/courses"}>Courses</Link>
        <Link to={"/instructors"}>Instructors</Link>
        <Link to={"/about"}>About</Link>
      </nav>
    </header>
  );
}
