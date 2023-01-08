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
                    "data"=>$user
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
            $user = new User;
            $user->user_id = $request->user_id;
            $user->username = $request->username;
            $user->name = $request->name;
            $user->save();
            return response()->json([
                "message" => "store berhasil",
                "data" => $user
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
    public function show($id)
    {
        $user = User::find($id);
        if ($user) {
            return response()->json([
                $user
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
            $user = User::find($id);
            $user->username = $request->username;
            $user->name = $request->name;
            $user->profile_picture = $request->profile_picture;
            $user->save();
            return response()->json([
                "message" => "update berhasil",
                "data" => $user
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
        $user = User::find($id);
        if($user){
            $user->delete();
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
