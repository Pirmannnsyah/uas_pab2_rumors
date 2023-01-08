<?php

namespace App\Http\Controllers\API;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use App\Models\User;

class userController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        try {
            $user = User::all();
            if($user){
                return response()->json([
                    "message" => "Data berhasil di ambil",
                    "data"=>$user
                ],200);
            } else {
                return response()->json([
                    "message" => "Gagal Mengambil data"
                ],400);
            }
            
        } catch (\Exception $e) {
            return response()->json([
                "message" => "Database Error"
            ],400);
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
            $user = new User;
            $user->user_id = $request->user_id;
            $user->username = $request->username;
            $user->name = $request->name;
            $user->save();
            return response()->json([
                "message" => "store berhasil",
                "data" => $user
            ],200);
        } catch (\Exception $e) {
            return response()->json([
                "message" => "Gagal store data"
            ],400);
        }

    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        $user = User::find($id);
        if ($user) {
            return response()->json([
                $user
            ],200);
        } else {
            return response()->json([
                "message" => "Gagal store data"
            ],400);
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
            $user = User::find($id);
            $user->username = $request->username;
            $user->name = $request->name;
            $user->profile_picture = $request->profile_picture;
            $user->save();
            return response()->json([
                "message" => "update berhasil",
                "data" => $user
            ],200);
        } catch (\Exception $e) {
            return response()->json([
                "message" => "Gagal update data"
            ],400);
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
        $user = User::find($id);
        if($user){
            $user->delete();
            return response()->json([
                "message" => "Berhasil menghapus data"
            ],200);
        } else {
            return response()->json([
                "message" => "Gagal menghapus data"
            ],400);
        }

    }
}
