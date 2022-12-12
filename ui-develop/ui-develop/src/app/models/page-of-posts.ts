export class PageOfPosts <T>{
    // posts:Array<object>
    // posts:Array<Post>
    // hasNext:boolean
    // totalPosts:number

    items:T[] = []
    hasNext: boolean = false
    totalElements:number = 0

}
