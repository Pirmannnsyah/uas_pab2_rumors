<?php

namespace App\Http\Controllers\API;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use App\Models\Like;

class likeController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        
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
            $Like = new Like;
            $Like->user_id = $request->user_id;
            $Like->post_id = $request->post_id;
            $Like->save();
            return response()->json([
                "message" => "store berhasil",
                "data" => $Like
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
    public function show($post_id)
    {
        $Like = Like::where("post_id","=",$post_id)->get();
        if ($Like) {
            return response()->json([
                "data"=>$Like
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
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $Like = Like::find($id);
        if($Like){
            $Like->delete();
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
