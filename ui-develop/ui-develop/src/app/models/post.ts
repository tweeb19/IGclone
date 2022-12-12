import { PageOfPosts } from "./page-of-posts";
import { User } from "./user";

export interface Post {
	id: number
	user: User
	img: string
	description: string	
	createdOn: Date
	comment: PageOfPosts<Comment>
}