<?php

namespace App\Http\Controllers\API;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use App\Models\Post;

class postController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        try {
            $Post = Post::all();
            if($Post){
                return response()->json([
                    "data"=>$Post
                ]);
            } else {
                return response()->json([
                    "message" => "Gagal Mengambil data"
                ]);
            }
            
        } catch (\Exception $e) {
            return response()->json([
                "message" => "Database Error"
            ]);
        }
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        try {
            $Post = new Post;
            $Post->user_id = $request->user_id;
            $Post->kategori = $request->kategori;
            $Post->judul = $request->judul;
            $Post->content = $request->content;
            $Post->save();
            return response()->json([
                "message" => "store berhasil",
                "data" => $Post
            ]);
        } catch (\Exception $e) {
            return response()->json([
                "message" => "Gagal store data"
            ]);
        }

    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($kategori)
    {
        $Post = Post::where("kategori","=",$kategori);
        if ($Post) {
            return response()->json([
                "data"=>$Post
            ]);
        } else {
            return response()->json([
                "message" => "Gagal store data"
            ]);
        }
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        try {
            $Post = Post::find($id);
            $Post->kategori = $request->kategori;
            $Post->judul = $request->judul;
            $Post->content = $request->content;
            $Post->save();
            return response()->json([
                "message" => "update berhasil",
                "data" => $Post
            ]);
        } catch (\Exception $e) {
            return response()->json([
                "message" => "Gagal update data"
            ]);
        }
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $Post = Post::find($id);
        if($Post){
            $Post->delete();
            return response()->json([
                "message" => "Berhasil menghapus data"
            ]);
        } else {
            return response()->json([
                "message" => "Gagal menghapus data"
            ]);
        }

    }
}
